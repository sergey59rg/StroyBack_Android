package com.rbmstroy.rbmbonus.features.registration.ui

interface ConfirmView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun congratulate()

}