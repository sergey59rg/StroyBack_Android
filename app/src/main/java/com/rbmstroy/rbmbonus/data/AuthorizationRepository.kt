package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.AuthResponse
import io.reactivex.Single

class AuthorizationRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun auth(email: String, pass: String): Single<AuthResponse> {
        val params = HashMap<String?, String?>()
        params["email"] = email
        params["pass"] = pass
        return apiRetrofit.auth(params)
    }
}