package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.UpdateRepository
import com.rbmstroy.rbmbonus.model.UpdateResponse
import io.reactivex.Single

interface UpdateInterceptor {

    fun update(phone: String, pass: String): Single<UpdateResponse>

}

class UpdateInterceptorImpl(private val updateRepository: UpdateRepository) : UpdateInterceptor {

    override fun update(phone: String, pass: String): Single<UpdateResponse> = updateRepository.update(phone, pass)

}