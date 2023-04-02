package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.RegisterResponse
import io.reactivex.Single

class RegistrationRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun register(geo: String, mail: String, manager: String, phone: String, pass: String, organization: String, username: String): Single<RegisterResponse> {
        val params = HashMap<String?, String?>()
        params["geo"] = geo
        params["mail"] = mail
        params["manager"] = manager
        params["phone"] = phone
        params["pass"] = pass
        params["organization"] = organization
        params["username"] = username
        return apiRetrofit.register(params)
    }
}