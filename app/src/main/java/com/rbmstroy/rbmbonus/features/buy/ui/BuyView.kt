package com.rbmstroy.rbmbonus.features.buy.ui

import com.rbmstroy.rbmbonus.model.Buy

interface BuyView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun getBrend(data: Buy)

    fun success()

    fun error(error: String, text: String, geo: String = "")

}