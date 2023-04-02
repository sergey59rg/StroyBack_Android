package com.rbmstroy.rbmbonus.features.promotion.ui

import com.rbmstroy.rbmbonus.model.PromotionResponse

interface PromotionView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun load(data: PromotionResponse)

}