package com.rbmstroy.rbmbonus.features.history.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.History


class HistoryAdapter(var data: List<History>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {

    }

    fun add(data: List<History>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        holder as HistoryViewHolder
        holder.price?.text = data.balance
        holder.datetime?.text = data.dataEvent
        if (data.imgIndex == "0") {
            holder.image?.setImageResource(R.drawable.accept)
        } else {
            holder.image?.setImageResource(R.drawable.buy)
        }
        if (data.name == "QR Code") {
            holder.text?.text = "${holder.parent.context.getString(R.string.scan_qr_code)}"
        } else {
            holder.text?.text = "${holder.parent.context.getString(R.string.card_purchase)} ${data.name}"
        }
    }

    override fun getItemCount(): Int = data.size

}