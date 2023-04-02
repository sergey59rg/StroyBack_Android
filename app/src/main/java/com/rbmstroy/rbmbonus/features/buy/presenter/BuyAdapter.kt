package com.rbmstroy.rbmbonus.features.buy.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.Buy
import com.squareup.picasso.Picasso

class BuyAdapter(var data: List<Buy>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewSetClickListener: ((Buy) ->Unit)? = null

    init {

    }

    fun add(data: List<Buy>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.buy_item, parent, false)
        return BuyViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        holder as BuyViewHolder
        if (data.img.isNotEmpty()) {
            Picasso.with(holder.image?.context).load(data.img).into(holder.image)
        }
        holder.image?.setClipToOutline(true)
        holder.price?.text = "${data.nominal} ${holder.parent.context.getString(R.string.rub)}"
        if (data.imgid == 7) {
            holder.text?.text = "${holder.parent.context.getString(R.string.money_participation_lottery)}"
        } else {
            holder.text?.text = data.name
        }
        holder.cardView?.setOnClickListener { viewSetClickListener?.invoke(data) }
    }

    override fun getItemCount(): Int = data.size

}