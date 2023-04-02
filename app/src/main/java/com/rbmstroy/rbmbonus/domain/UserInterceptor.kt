package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.UserRepository
import com.rbmstroy.rbmbonus.model.UserResponse
import io.reactivex.Single

interface UserInterceptor {

    fun user(token: String): Single<UserResponse>

}

class UserInterceptorImpl(private val userRepository: UserRepository) : UserInterceptor {

    override fun user(token: String): Single<UserResponse> = userRepository.user(token)

}