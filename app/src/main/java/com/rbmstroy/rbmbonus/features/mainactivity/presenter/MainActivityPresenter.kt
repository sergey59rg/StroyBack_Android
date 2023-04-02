package com.rbmstroy.rbmbonus.features.mainactivity.presenter

import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptor
import com.rbmstroy.rbmbonus.features.mainactivity.ui.MainActivityView

class MainActivityPresenter constructor(
    private val view: MainActivityView,
    private val preferenceInteractor: PreferenceInterceptor
) : BasePresenter()
{
    init {
        //preferenceInteractor.removeKeyStorage("token")
        if (preferenceInteractor.getKeyStorage("token") == null) {
            view.navigateToLogin()
        }
    }
}