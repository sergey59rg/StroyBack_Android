package com.rbmstroy.rbmbonus.features.splashactivity.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.rbmstroy.rbmbonus.BaseActivity
import com.rbmstroy.rbmbonus.Constants
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.mainactivity.ui.MainActivity
import com.rbmstroy.rbmbonus.features.splashactivity.presenter.SplashActivityPresenter

class SplashActivity : BaseActivity(), SplashActivityView {

    private lateinit var presenter: SplashActivityPresenter
    private var mDelayHandler: Handler? = null
    private val delay = Constants.SPLASH_SCREEN_DELAY

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        presenter = SplashActivityPresenter(this)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, delay.toLong())
    }

}