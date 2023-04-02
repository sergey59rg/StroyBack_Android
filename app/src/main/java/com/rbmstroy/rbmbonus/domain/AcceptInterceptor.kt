package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.AcceptRepository
import com.rbmstroy.rbmbonus.model.AcceptResponse
import io.reactivex.Single

interface AcceptInterceptor {

    fun accept(token: String, cardName: String, cardBalance: String): Single<AcceptResponse>

}

class AcceptInterceptorImpl(private val acceptRepository: AcceptRepository) : AcceptInterceptor {

    override fun accept(token: String, cardName: String, cardBalance: String): Single<AcceptResponse> = acceptRepository.accept(token, cardName, cardBalance)

}