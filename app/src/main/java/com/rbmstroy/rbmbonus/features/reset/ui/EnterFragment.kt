package com.rbmstroy.rbmbonus.features.reset.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.ForgottenRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.ForgottenInterceptorImpl
import com.rbmstroy.rbmbonus.features.reset.presenter.EnterPresenter
import com.rbmstroy.rbmbonus.utils.*
import kotlinx.android.synthetic.main.enter_fragment.*

class EnterFragment: Fragment(), EnterView {

    private lateinit var presenter: EnterPresenter
    private lateinit var code: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.enter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        code = arguments?.getString("code")!!
        text.setText("${getString(R.string.enter_pin_code)} ${arguments?.getString("email")!!}")
        confirmCode1!!.addTextChangedListener(PinFormattingTextWatcher())
        confirmCode2!!.addTextChangedListener(PinFormattingTextWatcher())
        confirmCode3!!.addTextChangedListener(PinFormattingTextWatcher())
        confirmCode4!!.addTextChangedListener(PinFormattingTextWatcher())
        constraintLayout!!.setSafeOnClickListener {
            confirmCode1!!.clearFocus()
            confirmCode2!!.clearFocus()
            confirmCode3!!.clearFocus()
            confirmCode4!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
        }
        confirmCode1!!.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()) {
                confirmCode2!!.requestFocus()
            }
        }
        confirmCode2!!.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()) {
                confirmCode3!!.requestFocus()
            }
        }
        confirmCode3!!.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()) {
                confirmCode4!!.requestFocus()
            }
        }
        confirmCode4!!.doOnTextChanged { text, start, before, count ->
            if(text!!.isNotEmpty()) {
                confirmCode4!!.clearFocus()
                KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            }
        }
        repeat_sending!!.setSafeOnClickListener {
            if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                presenter.forgotten(arguments?.getString("email")!!)
            } else {
                showError(getString(R.string.no_internet))
            }
        }
        send_button!!.setSafeOnClickListener {
            if (confirmCode1.text.isNotEmpty() && confirmCode2.text.isNotEmpty() && confirmCode3.text.isNotEmpty() && confirmCode4.text.isNotEmpty()) {
                val sb = StringBuilder()
                sb.append(confirmCode1.text).append(confirmCode2.text).append(confirmCode3.text)
                    .append(confirmCode4.text)
                val enter_code = sb.toString()
                if (code == enter_code) {
                    val bundle = Bundle()
                    bundle.putString("email", arguments?.getString("email")!!)
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.nav_reset_fragment, bundle)
                } else {
                    showError(getString(R.string.error_incorrect_code))
                }
            } else {
                showError(getString(R.string.error_code_length))
            }
        }
    }

    private fun initDependency() {
        val forgottenRepository = ForgottenRepository(RetrofitFactory.apiRetrofit)
        val forgottenInterceptor = ForgottenInterceptorImpl(forgottenRepository)
        presenter = EnterPresenter(this, forgottenInterceptor)
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

    override fun updateCode(code: String) {
        this.code = code
    }

}