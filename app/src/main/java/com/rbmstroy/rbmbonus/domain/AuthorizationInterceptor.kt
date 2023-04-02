package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.AuthorizationRepository
import com.rbmstroy.rbmbonus.model.AuthResponse
import io.reactivex.Single

interface AuthorizationInterceptor {

    fun auth(email: String, pass: String): Single<AuthResponse>

}

class AuthorizationInterceptorImpl(private val authorizationRepository: AuthorizationRepository) :
    AuthorizationInterceptor {

    override fun auth(email: String, pass: String): Single<AuthResponse> = authorizationRepository.auth(email, pass)

}