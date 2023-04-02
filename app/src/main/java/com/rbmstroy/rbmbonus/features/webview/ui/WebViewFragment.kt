package com.rbmstroy.rbmbonus.features.webview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.webview.presenter.WebViewPresenter
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.webview_fragment.*

class WebViewFragment : Fragment(), WebViewView {

    private lateinit var presenter: WebViewPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        title.setText(arguments?.getString("title")!!)
        back!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDependency() {
        presenter = WebViewPresenter(this, webView)
        presenter.load(arguments?.getString("url")!!)
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
}