package com.rbmstroy.rbmbonus.features.accept.ui

interface AcceptView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun back()
}