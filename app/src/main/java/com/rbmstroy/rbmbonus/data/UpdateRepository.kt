package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.UpdateResponse
import io.reactivex.Single

class UpdateRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun update(phone: String, pass: String): Single<UpdateResponse> {
        val params = HashMap<String?, String?>()
        params["phone"] = phone
        params["pass"] = pass
        return apiRetrofit.update(params)
    }
}