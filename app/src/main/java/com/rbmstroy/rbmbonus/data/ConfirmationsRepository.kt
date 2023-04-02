package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.ConfirmationsResponse
import io.reactivex.Single

class ConfirmationsRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun confirmations(token: String): Single<ConfirmationsResponse> {
        return apiRetrofit.confirmations(token)
    }
}