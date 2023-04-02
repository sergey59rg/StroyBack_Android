package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.HistoryRepository
import com.rbmstroy.rbmbonus.model.HistoryResponse
import io.reactivex.Single

interface HistoryInterceptor {

    fun history(token: String): Single<HistoryResponse>

}

class HistoryInterceptorImpl(private val historyRepository: HistoryRepository) :
    HistoryInterceptor {

    override fun history(token: String): Single<HistoryResponse> = historyRepository.history(token)

}