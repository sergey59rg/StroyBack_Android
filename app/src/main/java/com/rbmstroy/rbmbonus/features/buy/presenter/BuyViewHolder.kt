package com.rbmstroy.rbmbonus.features.buy.presenter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R

class BuyViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {
    var cardView: CardView? = null
    var image: ImageView? = null
    var text: TextView? = null
    var price: TextView? = null

    init {
        cardView = parent.findViewById(R.id.rootCardView)
        image = parent.findViewById(R.id.image)
        text = parent.findViewById(R.id.text)
        price = parent.findViewById(R.id.price)
    }
}