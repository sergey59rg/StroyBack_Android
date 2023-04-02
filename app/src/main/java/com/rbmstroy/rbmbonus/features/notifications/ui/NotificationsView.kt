package com.rbmstroy.rbmbonus.features.notifications.ui

import com.rbmstroy.rbmbonus.model.Notifications

interface NotificationsView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun promotion(data: Notifications)
}