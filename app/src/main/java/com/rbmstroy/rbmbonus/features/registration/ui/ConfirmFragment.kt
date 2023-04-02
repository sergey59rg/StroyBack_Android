package com.rbmstroy.rbmbonus.features.registration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.ConfirmRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.ConfirmInterceptorImpl
import com.rbmstroy.rbmbonus.features.registration.presenter.ConfirmPresenter
import com.rbmstroy.rbmbonus.utils.*
import kotlinx.android.synthetic.main.confirm_fragment.*

class ConfirmFragment: Fragment(), ConfirmView {

    private lateinit var presenter: ConfirmPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.confirm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        text.setText("${getString(R.string.enter_last_digits)} ${arguments?.getString("number")!!}")
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
        confirm_button!!.setSafeOnClickListener {
            if (confirmCode1.text.isNotEmpty() && confirmCode2.text.isNotEmpty() && confirmCode3.text.isNotEmpty() && confirmCode4.text.isNotEmpty()) {
                KeyboardUtils.hideKeyboard(requireActivity(), requireView())
                val sb = StringBuilder()
                sb.append(confirmCode1.text).append(confirmCode2.text).append(confirmCode3.text)
                    .append(confirmCode4.text)
                val code = sb.toString()
                if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                    presenter.confirm(code, arguments?.getString("token")!!)
                } else {
                    showError(getString(R.string.no_internet))
                }
            } else {
                showError(getString(R.string.error_code_length))
            }
        }
    }

    private fun initDependency() {
        val confirmRepository = ConfirmRepository(RetrofitFactory.apiRetrofit)
        val confirmInterceptor = ConfirmInterceptorImpl(confirmRepository)
        presenter = ConfirmPresenter(this, confirmInterceptor)
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

    override fun congratulate() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_congratulate_fragment)
    }

}