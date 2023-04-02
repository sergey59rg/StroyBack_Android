package com.rbmstroy.rbmbonus.features.profile.presenter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R

class ProfileViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {
    var cardView: CardView? = null
    var image: ImageView? = null
    var text: TextView? = null

    init {
        cardView = parent.findViewById(R.id.rootCardView)
        image = parent.findViewById(R.id.image)
        text = parent.findViewById(R.id.text)
    }
}