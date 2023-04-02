package com.rbmstroy.rbmbonus.features.notifications.presenter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R

class NotificationsViewHolder(val parent: View, val context: Context) : RecyclerView.ViewHolder(parent) {
    var cardView: CardView? = null
    var title: TextView? = null
    var message: TextView? = null
    var datetime: TextView? = null
    var promotion: TextView? = null

    init {
        cardView = parent.findViewById(R.id.rootCardView)
        title = parent.findViewById(R.id.title)
        message = parent.findViewById(R.id.message)
        datetime = parent.findViewById(R.id.datetime)
        promotion = parent.findViewById(R.id.promotion)
    }
}