package com.rbmstroy.rbmbonus.features.home.ui

import com.rbmstroy.rbmbonus.model.UserResponse
import com.rbmstroy.rbmbonus.model.VersionResponse

interface HomeView {

    fun showError(error: String)

    fun update(data: UserResponse)

    fun version(data: VersionResponse)

    fun exit()

}