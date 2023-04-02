package com.rbmstroy.rbmbonus.domain

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.d("ErrorInterceptor", response.body().toString())
        return response
    }
}