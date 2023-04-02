package com.rbmstroy.rbmbonus.data

import com.rbmstroy.rbmbonus.data.sqlite.DatabaseHandler
import com.rbmstroy.rbmbonus.model.Notifications

interface NotificationsInterceptor {

    fun addNotifications(notifications: Notifications): Boolean

    fun getNotifications(): ArrayList<Notifications>

    fun deleteNotifications(id: Int): Boolean

    fun updateNotifications(id: Int): Boolean

    fun delete(): Boolean
}

class NotificationsInterceptorImpl(private val dbHandler: DatabaseHandler) : NotificationsInterceptor {

    override fun addNotifications(notifications: Notifications): Boolean {
        return dbHandler.addNotifications(notifications)
    }

    override fun getNotifications(): ArrayList<Notifications> = dbHandler.getNotifications()

    override fun deleteNotifications(id: Int): Boolean {
        return dbHandler.deleteNotifications(id)
    }

    override fun updateNotifications(id: Int): Boolean {
        return dbHandler.updateNotifications(id)
    }

    override fun delete(): Boolean {
        return dbHandler.deleteTable("NOTIFICATIONS")
    }
}