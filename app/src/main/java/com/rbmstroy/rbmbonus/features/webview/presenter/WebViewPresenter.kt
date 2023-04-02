package com.rbmstroy.rbmbonus.features.webview.presenter

import android.net.http.SslError
import com.rbmstroy.rbmbonus.BasePresenter
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rbmstroy.rbmbonus.features.webview.ui.WebViewView

class WebViewPresenter constructor(
    private val myView: WebViewView,
    private val webView: WebView
) : BasePresenter() {

    private val MAX_PROGRESS = 100

    init {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < MAX_PROGRESS) {
                    myView.showProgress()
                }
                if (newProgress == MAX_PROGRESS) {
                    myView.hideProgress()
                }
            }
        }
    }

    fun load(url: String) {
        webView.loadUrl(url)
    }
}