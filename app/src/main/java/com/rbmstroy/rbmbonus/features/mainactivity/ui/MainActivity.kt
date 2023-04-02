package com.rbmstroy.rbmbonus.features.mainactivity.ui

import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.rbmstroy.rbmbonus.BaseActivity
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.NotificationsInterceptorImpl
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.data.sqlite.DatabaseHandler
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.mainactivity.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity(), MainActivityView {

    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        FirebaseMessaging.getInstance().subscribeToTopic("android")
        val prefs = Preference()
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        presenter = MainActivityPresenter(this, preferenceInterceptor)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id)
            {
                R.id.nav_home_fragment -> {
                    nav_view.visibility = VISIBLE
                }
                R.id.nav_history_fragment -> {
                    nav_view.visibility = VISIBLE
                }
                R.id.nav_confirmations_fragment -> {
                    nav_view.visibility = VISIBLE
                }
                R.id.nav_profile_fragment -> {
                    nav_view.visibility = VISIBLE
                }
                else -> {
                    nav_view.visibility = GONE
                }
            }
        }
        val handler = Handler()
        handler.postDelayed({
            val extras = intent.extras
            if (extras != null) {
                val id = extras.getInt("notification_id")
                val dbHandler = DatabaseHandler(this, null)
                val notificationsInterceptor = NotificationsInterceptorImpl(dbHandler)
                notificationsInterceptor.updateNotifications(id)
                val bundle = Bundle()
                bundle.putInt("id", id)
                navController.navigate(R.id.nav_promotion_fragment, bundle)
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun navigateToLogin() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.navigate(R.id.nav_authorization_fragment)
    }

}