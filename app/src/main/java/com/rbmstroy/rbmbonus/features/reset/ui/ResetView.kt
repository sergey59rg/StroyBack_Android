package com.rbmstroy.rbmbonus.features.reset.ui

interface ResetView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun auth()
}