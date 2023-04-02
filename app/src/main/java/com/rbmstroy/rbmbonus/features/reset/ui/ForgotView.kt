package com.rbmstroy.rbmbonus.features.reset.ui

interface ForgotView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun enter(code: String)

}