package com.rbmstroy.rbmbonus.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.NotificationsInterceptorImpl
import com.rbmstroy.rbmbonus.data.sqlite.DatabaseHandler
import com.rbmstroy.rbmbonus.features.mainactivity.ui.MainActivity
import com.rbmstroy.rbmbonus.model.Notifications
import com.rbmstroy.rbmbonus.utils.DateUtils
import java.util.*


class FCMService: FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "Channel1"
        private const val CHANNEL_NAME = "Notification"
    }

    init {

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Token new: ${token}")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.getData().size > 0) {
            Log.d(TAG, "Message data payload ${remoteMessage.data}")
            val dbHandler = DatabaseHandler(this, null)
            val notificationsInterceptor = NotificationsInterceptorImpl(dbHandler)
            var id: Int = (1000..100000).random()
            if (remoteMessage.data.get("type")!! == "promotion") {
                id = remoteMessage.data.get("id")!!.toInt()
            }
            val notifications = Notifications(id, remoteMessage.data.get("type")!!, remoteMessage.data.get("title")!!, remoteMessage.data.get("body")!!, DateUtils.dateToString(
                Date(), "yyyy-MM-dd HH:mm:ss"), 0)
            notificationsInterceptor.addNotifications(notifications)
            sendNotification(notifications)
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message notification payload ${remoteMessage.notification}")
        }
    }

    private fun sendNotification(notifications: Notifications) {
        initChannel(CHANNEL_ID, CHANNEL_NAME)
        val intent = Intent(this, MainActivity::class.java)
        if (notifications.type == "promotion") {
            intent.putExtra("notification_id", notifications.id)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
        val pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent, PendingIntent.FLAG_IMMUTABLE)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notifications.title)
            .setContentText(notifications.message)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(null, notifications.id, notificationBuilder.build())
    }

    private fun initChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

}