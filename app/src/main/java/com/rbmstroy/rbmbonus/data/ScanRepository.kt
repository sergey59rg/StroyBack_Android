package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.ScanResponse
import io.reactivex.Single


class ScanRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun scan(token: String, geo: String, qr_code: String): Single<ScanResponse> {
        val params = HashMap<String?, String?>()
        params["token"] = token
        params["geo"] = geo
        params["qr_code"] = qr_code
        return apiRetrofit.scan(params)
    }
}