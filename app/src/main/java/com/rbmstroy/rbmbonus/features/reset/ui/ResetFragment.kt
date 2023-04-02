package com.rbmstroy.rbmbonus.features.reset.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.UpdateRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.UpdateInterceptorImpl
import com.rbmstroy.rbmbonus.features.reset.presenter.ResetPresenter
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.KeyboardUtils
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.reset_fragment.*

class ResetFragment: Fragment(), ResetView {

    private lateinit var presenter: ResetPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reset_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        constraintLayout!!.setSafeOnClickListener {
            password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = false
            password_editText!!.clearFocus()
            new_password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = false
            new_password_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
        }
        password_editText.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
            if (b) {
                password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = true
            } else {
                password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = false
            }
        }
        new_password_editText.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
            if (b) {
                new_password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = true
            } else {
                new_password_TextInputLayout!!.isPasswordVisibilityToggleEnabled = false
            }
        }
        save_button!!.setSafeOnClickListener {
            if (password_editText.length() < 5) {
                showError(getString(R.string.error_password_length))
                return@setSafeOnClickListener
            }
            password_editText!!.clearFocus()
            if (new_password_editText.length() < 5) {
                showError(getString(R.string.error_new_password_length))
                return@setSafeOnClickListener
            }
            new_password_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            if (password_editText!!.text.toString() == new_password_editText!!.text.toString()) {
                if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                    presenter.update(
                        arguments?.getString("email")!!,
                        password_editText!!.text.toString()
                    )
                } else {
                    showError(getString(R.string.no_internet))
                }
            } else {
                showError(getString(R.string.error_password_dont_match))
            }
        }
        cancel_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.backQueue.reversed().forEach {
                if (it.destination.label != null) {
                    navController.popBackStack(it.destination.id, true)
                }
            }
            navController.navigate(R.id.nav_authorization_fragment)
        }
    }

    private fun initDependency() {
        val updateRepository = UpdateRepository(RetrofitFactory.apiRetrofit)
        val updateInterceptor = UpdateInterceptorImpl(updateRepository)
        presenter = ResetPresenter(this, updateInterceptor)
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
        navController.navigate(R.id.nav_authorization_fragment)
    }
}