package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.RegistrationRepository
import com.rbmstroy.rbmbonus.model.RegisterResponse
import io.reactivex.Single

interface RegistrationInterceptor {

    fun register(geo: String, mail: String, manager: String, phone: String, pass: String, organization: String, username: String): Single<RegisterResponse>

}

class RegistrationInterceptorImpl(private val registrationRepository: RegistrationRepository) :
    RegistrationInterceptor {

    override fun register(geo: String, mail: String, manager: String, phone: String, pass: String, organization: String, username: String): Single<RegisterResponse> = registrationRepository.register(geo, mail, manager, phone, pass, organization, username)

}