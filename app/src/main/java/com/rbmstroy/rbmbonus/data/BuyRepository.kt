package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.BuyResponse
import com.rbmstroy.rbmbonus.model.ShopResponse
import io.reactivex.Single

class BuyRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun card(): Single<BuyResponse> {
        return apiRetrofit.card()
    }

    fun shop(name: String, price: String, token: String): Single<ShopResponse> {
        val params = HashMap<String?, String?>()
        params["cardName"] = name
        params["price"] = price
        params["token"] = token
        return apiRetrofit.shop(params)
    }
}