package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.VersionRepository
import com.rbmstroy.rbmbonus.model.VersionResponse
import io.reactivex.Single

interface VersionInterceptor {

    fun version(): Single<VersionResponse>

}

class VersionInterceptorImpl(private val versionRepository: VersionRepository) : VersionInterceptor {

    override fun version(): Single<VersionResponse> = versionRepository.version()

}