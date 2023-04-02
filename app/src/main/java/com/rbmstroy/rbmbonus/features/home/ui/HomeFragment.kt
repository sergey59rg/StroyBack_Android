package com.rbmstroy.rbmbonus.features.home.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.BuildConfig
import com.rbmstroy.rbmbonus.Constants
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.NotificationsInterceptorImpl
import com.rbmstroy.rbmbonus.data.UserRepository
import com.rbmstroy.rbmbonus.data.VersionRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.data.sqlite.DatabaseHandler
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.domain.UserInterceptorImpl
import com.rbmstroy.rbmbonus.domain.VersionInterceptorImpl
import com.rbmstroy.rbmbonus.features.home.presenter.HomePresenter
import com.rbmstroy.rbmbonus.model.UserResponse
import com.rbmstroy.rbmbonus.model.VersionResponse
import com.rbmstroy.rbmbonus.utils.*
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment: Fragment(), HomeView {

    private lateinit var presenter: HomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        circle.isVisible = false
        initDependency()
        info_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_info_fragment)
        }
        bell_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_notifications_fragment)
        }
        scan_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_scan_fragment)
        }
        buy_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_buy_fragment)
        }
        awards_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_awards_fragment)
        }
    }

    private fun initDependency() {
        val prefs = Preference()
        val dbHandler = DatabaseHandler(requireContext(), null)
        val notificationsInterceptor = NotificationsInterceptorImpl(dbHandler)
        val userRepository = UserRepository(RetrofitFactory.apiRetrofit)
        val userInterceptor = UserInterceptorImpl(userRepository)
        val versionRepository = VersionRepository(RetrofitFactory.apiRetrofit)
        val versionInterceptor = VersionInterceptorImpl(versionRepository)
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        if (preferenceInterceptor.getKeyStorage("username") != null) {
            fullname.setText(preferenceInterceptor.getKeyStorage("username").toString())
        }
        if (preferenceInterceptor.getKeyStorage("balance") != null) {
            balance.setText("${preferenceInterceptor.getKeyStorage("balance")} ${getString(R.string.rub)}")
            personal_balance.setText("${preferenceInterceptor.getKeyStorage("balance")} ${getString(R.string.rub)}")
        }
        if (preferenceInterceptor.getKeyStorage("id") != null) {
            personal_number.setText("${preferenceInterceptor.getKeyStorage("id")}")
        }
        val data = notificationsInterceptor.getNotifications()
        if (data.size != 0) {
            for(i in 0..data.size-1) {
                if (data[i].isRead == 0) {
                    circle.isVisible = true
                    break
                }
            }
        }
        presenter = HomePresenter(this, userInterceptor, versionInterceptor, preferenceInterceptor)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.user(preferenceInterceptor.getKeyStorage("token").toString())
        } else {
            showError(getString(R.string.no_internet))
        }
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    override fun showError(error: String) {
        CustomDialog(requireContext()).show(getString(R.string.error), error, arrayOf(getString(R.string.ok))) { }
    }

    override fun update(data: UserResponse) {
        fullname.setText(data.username)
        balance.setText("${data.balance} ${getString(R.string.rub)}")
        personal_balance.setText("${data.balance} ${getString(R.string.rub)}")
        personal_number.setText("${data.id}")
        presenter.version()
    }

    override fun version(data: VersionResponse) {
        val storeVersion = "${data.android.last_version}"
        val currentVersion = "${BuildConfig.VERSION_NAME}.${BuildConfig.VERSION_CODE}"
        println("storeVersion: ${storeVersion}")
        println("currentVersion: ${currentVersion}")
        if (currentVersion.compareTo(storeVersion) < 0 ) {
            CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.new_version_released), arrayOf(getString(R.string.ok))) {
                if (data.android.update_required) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_MARKET)))
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_URL)))
                    }
                }
            }
        }
    }

    override fun exit() {
        LogoutUtils.logout(requireActivity())
    }

}