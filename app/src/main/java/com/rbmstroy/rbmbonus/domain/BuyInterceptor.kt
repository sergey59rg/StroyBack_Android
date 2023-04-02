package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.BuyRepository
import com.rbmstroy.rbmbonus.model.BuyResponse
import com.rbmstroy.rbmbonus.model.ShopResponse
import io.reactivex.Single


interface BuyInterceptor {

    fun card(): Single<BuyResponse>

    fun shop(name: String, price: String, token: String): Single<ShopResponse>

}

class BuyInterceptorImpl(private val buyRepository: BuyRepository) : BuyInterceptor {

    override fun card(): Single<BuyResponse> = buyRepository.card()

    override fun shop(name: String, price: String, token: String): Single<ShopResponse> = buyRepository.shop(name, price, token)

}