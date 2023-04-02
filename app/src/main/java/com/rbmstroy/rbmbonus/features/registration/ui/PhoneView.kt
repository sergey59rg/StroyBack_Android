package com.rbmstroy.rbmbonus.features.registration.ui

interface PhoneView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun confirm(token: String, number: String)
}