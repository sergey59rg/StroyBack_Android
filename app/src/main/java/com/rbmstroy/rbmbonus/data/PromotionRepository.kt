package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.PromotionResponse
import io.reactivex.Single

class PromotionRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun promotion(id: Int): Single<PromotionResponse> {
        return apiRetrofit.promotion(id)
    }
}