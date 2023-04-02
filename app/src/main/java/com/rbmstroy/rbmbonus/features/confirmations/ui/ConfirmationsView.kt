package com.rbmstroy.rbmbonus.features.confirmations.ui

import com.rbmstroy.rbmbonus.model.Confirmations

interface ConfirmationsView {

    fun showError(error: String)

    fun getBrend(data: Confirmations)

}