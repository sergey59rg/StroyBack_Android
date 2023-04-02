package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.VersionResponse
import io.reactivex.Single

class VersionRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun version(): Single<VersionResponse> {
        return apiRetrofit.version()
    }
}