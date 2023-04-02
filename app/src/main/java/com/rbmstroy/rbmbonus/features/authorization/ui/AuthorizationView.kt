package com.rbmstroy.rbmbonus.features.authorization.ui

interface AuthorizationView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun auth()

}