package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.ForgottenRepository
import com.rbmstroy.rbmbonus.model.ForgottenResponse
import io.reactivex.Single

interface ForgottenInterceptor {

    fun forgotten(phone: String): Single<ForgottenResponse>

}

class ForgottenInterceptorImpl(private val forgottenRepository: ForgottenRepository) :
    ForgottenInterceptor {

    override fun forgotten(phone: String): Single<ForgottenResponse> = forgottenRepository.forgotten(phone)

}