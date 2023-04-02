package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.AcceptResponse
import io.reactivex.Single

class AcceptRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun accept(token: String, cardName: String, cardBalance: String): Single<AcceptResponse> {
        val params = HashMap<String?, String?>()
        params["token"] = token
        params["cardName"] = cardName
        params["cardBalance"] = cardBalance
        return apiRetrofit.accept(params)
    }
}