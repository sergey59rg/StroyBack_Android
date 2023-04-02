package com.rbmstroy.rbmbonus.features.notifications.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.Notifications

class NotificationsAdapter(var data: List<Notifications>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewSetClickListener: ((Notifications) ->Unit)? = null

    init {

    }

    fun add(data: List<Notifications>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun remove(position: Int): Int {
        return data[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notifications_item, parent, false)
        return NotificationsViewHolder(itemView, parent.context)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        holder as NotificationsViewHolder
        holder.title?.text = data.title
        holder.message?.text = data.message
        holder.datetime?.text = data.datetime
        if(data.type == "promotion") {
            holder.promotion?.isVisible = true
        } else {
            holder.promotion?.isVisible = false
        }
        if(data.isRead == 0) {
            holder.title?.setTextColor(holder.context.getResources().getColor(R.color.black))
            holder.message?.setTextColor(holder.context.getResources().getColor(R.color.black))
            holder.datetime?.setTextColor(holder.context.getResources().getColor(R.color.grey))
        } else {
            holder.title?.setTextColor(holder.context.getResources().getColor(R.color.grey_light))
            holder.message?.setTextColor(holder.context.getResources().getColor(R.color.grey_light))
            holder.datetime?.setTextColor(holder.context.getResources().getColor(R.color.grey_light))
        }
        holder.cardView?.setOnClickListener { viewSetClickListener?.invoke(data) }
    }

    override fun getItemCount(): Int = data.size

}