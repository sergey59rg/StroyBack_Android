package com.rbmstroy.rbmbonus.utils

import android.app.Activity
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl

class LogoutUtils {

    companion object {

        fun logout(activity: Activity) {
            val prefs = Preference()
            val preferenceInteractor = PreferenceInterceptorImpl(prefs)
            preferenceInteractor.logout()
            val navController = Navigation.findNavController(activity, R.id.nav_host_fragment)
            navController.backQueue.reversed().forEach {
                if (it.destination.label != null) {
                    navController.popBackStack(it.destination.id, true)
                }
            }
            navController.navigate(R.id.nav_authorization_fragment)
        }
    }
}