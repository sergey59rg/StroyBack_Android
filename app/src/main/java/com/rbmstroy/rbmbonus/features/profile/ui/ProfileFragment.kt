package com.rbmstroy.rbmbonus.features.profile.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbmstroy.rbmbonus.Constants
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.profile.presenter.ProfileAdapter
import com.rbmstroy.rbmbonus.features.profile.presenter.ProfilePresenter
import com.rbmstroy.rbmbonus.model.Profile
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.LogoutUtils
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.listRecycler

class ProfileFragment: Fragment(), ProfileView {

    private lateinit var presenter: ProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        scan_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_scan_fragment)
        }
        exit!!.setSafeOnClickListener {
            LogoutUtils.logout(requireActivity())
        }
    }

    private fun initDependency() {
        val prefs = Preference()
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        val adapter = ProfileAdapter(arrayListOf())
        listRecycler.layoutManager = LinearLayoutManager(requireContext())
        listRecycler.adapter = adapter
        presenter = ProfilePresenter(this, adapter, requireContext())
        if (preferenceInterceptor.getKeyStorage("username") != null) {
            fullname.setText(preferenceInterceptor.getKeyStorage("username").toString())
        }
        if (preferenceInterceptor.getKeyStorage("id") != null) {
            personal_number.setText("${preferenceInterceptor.getKeyStorage("id")}")
        }
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    override fun getProfile(data: Profile) {
        when (data.id) {
            0 -> {
                CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.functional_development), arrayOf(getString(R.string.ok))) { }
            }
            1 -> {
                CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.functional_development), arrayOf(getString(R.string.ok))) { }
            }
            2 -> {
                CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.functional_development), arrayOf(getString(R.string.ok))) { }
            }
            3 -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(Constants.SUPPORT_PHONE)))
                startActivity(intent)
            }
        }
    }


}