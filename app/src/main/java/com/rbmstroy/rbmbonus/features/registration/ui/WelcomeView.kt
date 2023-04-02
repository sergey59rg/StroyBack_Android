package com.rbmstroy.rbmbonus.features.registration.ui

interface WelcomeView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

}