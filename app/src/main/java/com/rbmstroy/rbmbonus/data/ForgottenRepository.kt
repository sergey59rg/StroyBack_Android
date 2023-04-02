package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.ForgottenResponse
import io.reactivex.Single

class ForgottenRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun forgotten(phone: String): Single<ForgottenResponse> {
        val params = HashMap<String?, String?>()
        params["phone"] = phone
        return apiRetrofit.forgotten(params)
    }
}