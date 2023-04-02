package com.rbmstroy.rbmbonus.features.registration.presenter

import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.features.registration.ui.WelcomeView

class WelcomePresenter constructor(
    private val view: WelcomeView
) : BasePresenter() {

    var isLocation = false

    init {

    }

}