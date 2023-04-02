package com.rbmstroy.rbmbonus.features.authorization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.Constants
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.AuthorizationRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.AuthorizationInterceptorImpl
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.authorization.presenter.AuthorizationPresenter
import com.rbmstroy.rbmbonus.utils.*
import kotlinx.android.synthetic.main.authorization_fragment.*

class AuthorizationFragment: Fragment(), AuthorizationView {

    private lateinit var presenter: AuthorizationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.authorization_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        support.setText(getString(R.string.support) + " " + Constants.SUPPORT_PHONE)
        constraintLayout!!.setSafeOnClickListener {
            email_editText!!.clearFocus()
            password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = false
            password_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
        }
        password_editText.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
            if (b) {
                password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = true
            } else {
                password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = false
            }
        }
        enter_button!!.setSafeOnClickListener {
            if (!EmailValidator.isValid(email_editText!!.text.toString())) {
                showError(getString(R.string.error_correct_email))
                return@setSafeOnClickListener
            }
            email_editText!!.clearFocus()
            if (password_editText.length() < 5) {
                showError(getString(R.string.error_password_length))
                return@setSafeOnClickListener
            }
            password_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                presenter.authorization(email_editText!!.text.toString(), password_editText!!.text.toString())
            } else {
                showError(getString(R.string.no_internet))
            }
        }
        forgot_password!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_forgot_fragment)
        }
        registration!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_welcome_fragment)
        }
    }

    private fun initDependency() {
        val prefs = Preference()
        val authorizationRepository = AuthorizationRepository(RetrofitFactory.apiRetrofit)
        val authorizationInterceptor = AuthorizationInterceptorImpl(authorizationRepository)
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        presenter = AuthorizationPresenter(this, authorizationInterceptor, preferenceInterceptor)
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        try {
            progressBar.visibility = View.GONE
        } catch (e: Exception) {

        }
    }

    override fun showError(error: String) {
        CustomDialog(requireContext()).show(getString(R.string.error), error, arrayOf(getString(R.string.ok))) { }
    }

    override fun auth() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.backQueue.reversed().forEach {
            if (it.destination.label != null) {
                navController.popBackStack(it.destination.id, true)
            }
        }
        navController.navigate(R.id.nav_home_fragment)
    }
}