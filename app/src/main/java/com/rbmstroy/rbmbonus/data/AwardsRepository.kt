package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.AwardsResponse
import io.reactivex.Single

class AwardsRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun product(): Single<AwardsResponse> {
        return apiRetrofit.product()
    }
}