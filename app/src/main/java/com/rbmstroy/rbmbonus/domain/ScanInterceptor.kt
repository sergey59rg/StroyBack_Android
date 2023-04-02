package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.ScanRepository
import com.rbmstroy.rbmbonus.model.ScanResponse
import io.reactivex.Single

interface ScanInterceptor {

    fun scan(token: String, geo: String, qr_code: String): Single<ScanResponse>

}

class ScanInterceptorImpl(private val scanRepository: ScanRepository) : ScanInterceptor {

    override fun scan(token: String, geo: String, qr_code: String): Single<ScanResponse> = scanRepository.scan(token, geo, qr_code)

}