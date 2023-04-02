package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.network.ApiRetrofit
import com.rbmstroy.rbmbonus.model.UserResponse
import io.reactivex.Single

class UserRepository constructor(
    private val apiRetrofit: ApiRetrofit
) {

    fun user(token: String): Single<UserResponse> {
        return apiRetrofit.user(token)
    }
}