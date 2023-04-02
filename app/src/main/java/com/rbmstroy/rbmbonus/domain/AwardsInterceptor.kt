package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.AwardsRepository
import com.rbmstroy.rbmbonus.model.AwardsResponse
import io.reactivex.Single

interface AwardsInterceptor {

    fun product(): Single<AwardsResponse>

}

class AwardsInterceptorImpl(private val awardsRepository: AwardsRepository) : AwardsInterceptor {

    override fun product(): Single<AwardsResponse> = awardsRepository.product()

}