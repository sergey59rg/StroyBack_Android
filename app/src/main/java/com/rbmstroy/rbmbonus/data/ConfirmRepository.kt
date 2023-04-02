package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.ConfirmResponse
import io.reactivex.Single

class ConfirmRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun confirm(code: String, token: String): Single<ConfirmResponse> {
        val params = HashMap<String?, String?>()
        params["code"] = code
        params["token"] = token
        return apiRetrofit.confirm(params)
    }
}