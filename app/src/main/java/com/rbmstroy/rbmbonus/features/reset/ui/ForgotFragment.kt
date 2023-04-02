package com.rbmstroy.rbmbonus.features.reset.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.ForgottenRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.ForgottenInterceptorImpl
import com.rbmstroy.rbmbonus.features.reset.presenter.ForgotPresenter
import com.rbmstroy.rbmbonus.utils.*
import kotlinx.android.synthetic.main.forgot_fragment.*
import kotlinx.android.synthetic.main.forgot_fragment.constraintLayout
import kotlinx.android.synthetic.main.forgot_fragment.email_editText
import kotlinx.android.synthetic.main.forgot_fragment.progressBar


class ForgotFragment: Fragment(), ForgotView {

    private lateinit var presenter: ForgotPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.forgot_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        constraintLayout!!.setSafeOnClickListener {
            email_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
        }
        send_button!!.setSafeOnClickListener {
            if (!EmailValidator.isValid(email_editText!!.text.toString())) {
                showError(getString(R.string.error_correct_email))
                return@setSafeOnClickListener
            }
            email_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                presenter.forgotten(email_editText!!.text.toString())
            } else {
                showError(getString(R.string.no_internet))
            }
        }
    }

    private fun initDependency() {
        val forgottenRepository = ForgottenRepository(RetrofitFactory.apiRetrofit)
        val forgottenInterceptor = ForgottenInterceptorImpl(forgottenRepository)
        presenter = ForgotPresenter(this, forgottenInterceptor)
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

    override fun enter(code: String) {
        val bundle = Bundle()
        bundle.putString("email", email_editText!!.text.toString())
        bundle.putString("code", code)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_enter_fragment, bundle)
    }
}