package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.ConfirmationsRepository
import com.rbmstroy.rbmbonus.model.ConfirmationsResponse
import io.reactivex.Single

interface ConfirmationsInterceptor {

    fun confirmations(token: String): Single<ConfirmationsResponse>

}

class ConfirmationsInterceptorImpl(private val confirmationsRepository: ConfirmationsRepository) :
    ConfirmationsInterceptor {

    override fun confirmations(token: String): Single<ConfirmationsResponse> = confirmationsRepository.confirmations(token)

}