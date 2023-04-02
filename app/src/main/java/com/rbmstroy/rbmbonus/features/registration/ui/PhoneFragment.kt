package com.rbmstroy.rbmbonus.features.registration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.RegistrationRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.RegistrationInterceptorImpl
import com.rbmstroy.rbmbonus.features.registration.presenter.PhonePresenter
import com.rbmstroy.rbmbonus.utils.*
import kotlinx.android.synthetic.main.phone_fragment.*

class PhoneFragment: Fragment(), PhoneView {

    private lateinit var presenter: PhonePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.phone_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        constraintLayout!!.setSafeOnClickListener {
            phone_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
        }
        phone_editText!!.addTextChangedListener(PhoneFormattingTextWatcher())
        phone_editText!!.doOnTextChanged { text, start, before, count ->
            if(phone_editText.length() >= 13) {
                phone_editText!!.clearFocus()
                KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            }
        }
        send_button!!.setSafeOnClickListener {
            val phone = "+${countryCodePicker.defaultCountryCode}${phone_editText!!.text.filter { !it.isWhitespace() }}"
            if (!PhoneValidator.isValid(phone)) {
                showError(getString(R.string.error_correct_phone))
                return@setSafeOnClickListener
            }
            phone_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                presenter.register(arguments?.getString("geo")!!, arguments?.getString("mail")!!, arguments?.getString("manager")!!, phone, arguments?.getString("pass")!!, arguments?.getString("organization")!!, arguments?.getString("username")!!)
            } else {
                showError(getString(R.string.no_internet))
            }
        }
    }

    private fun initDependency() {
        val registrationRepository = RegistrationRepository(RetrofitFactory.apiRetrofit)
        val registrationInterceptor = RegistrationInterceptorImpl(registrationRepository)
        presenter = PhonePresenter(this, registrationInterceptor)
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

    override fun confirm(token: String, number: String) {
        val bundle = Bundle()
        bundle.putString("number", number)
        bundle.putString("token", token)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_confirm_fragment, bundle)
    }

}