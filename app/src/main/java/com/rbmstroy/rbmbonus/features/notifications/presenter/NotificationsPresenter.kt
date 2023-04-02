package com.rbmstroy.rbmbonus.features.notifications.presenter

import android.annotation.SuppressLint
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.data.NotificationsInterceptor
import com.rbmstroy.rbmbonus.features.notifications.ui.NotificationsView
import com.rbmstroy.rbmbonus.model.Notifications

class NotificationsPresenter constructor(
    private val view: NotificationsView,
    private val notificationsInterceptor: NotificationsInterceptor,
    private val notificationsAdapter: NotificationsAdapter
) : BasePresenter() {

    init {
        notificationsAdapter.viewSetClickListener = ::viewSetOnClick
    }

    fun load() {
        notificationsAdapter.add(notificationsInterceptor.getNotifications())
    }

    fun update() {
        val data = notificationsInterceptor.getNotifications()
        for (i in 0..data.size-1) {
            notificationsInterceptor.updateNotifications(data[i].id)
        }
        notificationsAdapter.add(notificationsInterceptor.getNotifications())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewSetOnClick(data: Notifications) {
        notificationsInterceptor.updateNotifications(data.id)
        notificationsAdapter.add(notificationsInterceptor.getNotifications())
        if(data.type == "promotion") {
            view.promotion(data)
        }
    }

}