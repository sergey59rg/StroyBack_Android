package com.rbmstroy.rbmbonus.features.reset.ui

interface EnterView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun updateCode(code: String)

}