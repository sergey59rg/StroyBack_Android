package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.ConfirmRepository;
import com.rbmstroy.rbmbonus.model.ConfirmResponse;
import io.reactivex.Single;

interface ConfirmInterceptor {

    fun confirm(code: String, token: String): Single<ConfirmResponse>

}

class ConfirmInterceptorImpl(private val confirmRepository: ConfirmRepository) :
    ConfirmInterceptor {

    override fun confirm(code: String, token: String): Single<ConfirmResponse> = confirmRepository.confirm(code, token)

}