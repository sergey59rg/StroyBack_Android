package com.rbmstroy.rbmbonus.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class ConnectionDetector {

    companion object {

        fun ConnectingToInternet(context: Context): Boolean {
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = connectivity.getNetworkCapabilities(connectivity.activeNetwork)
            if(capabilities != null ){
                if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    return true
                }
                else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return true
                }
            }
            return false
        }
    }
}