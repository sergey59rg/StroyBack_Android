package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.PromotionRepository
import com.rbmstroy.rbmbonus.model.PromotionResponse
import io.reactivex.Single

interface PromotionInterceptor {

    fun promotion(id: Int): Single<PromotionResponse>

}

class PromotionInterceptorImpl(private val promotionRepository: PromotionRepository) :
    PromotionInterceptor {

    override fun promotion(id: Int): Single<PromotionResponse> = promotionRepository.promotion(id)

}