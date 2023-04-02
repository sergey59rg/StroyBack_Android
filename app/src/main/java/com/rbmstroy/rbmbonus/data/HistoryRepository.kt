package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.HistoryResponse
import io.reactivex.Single

class HistoryRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun history(token: String): Single<HistoryResponse> {
        return apiRetrofit.history(token)
    }
}