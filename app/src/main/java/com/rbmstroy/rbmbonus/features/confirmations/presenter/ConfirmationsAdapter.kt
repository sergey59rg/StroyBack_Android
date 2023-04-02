package com.rbmstroy.rbmbonus.features.confirmations.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.Confirmations
import com.squareup.picasso.Picasso

class ConfirmationsAdapter(var data: List<Confirmations>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewSetClickListener: ((Confirmations) ->Unit)? = null

    init {

    }

    fun add(data: List<Confirmations>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.confirmations_item, parent, false)
        return ConfirmationsViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        holder as ConfirmationsViewHolder
        holder.price?.text = data.balance
        if (data.img.isNotEmpty()) {
            Picasso.with(holder.image?.context).load(data.img).into(holder.image)
        }
        holder.image?.setClipToOutline(true)
        holder.price?.text = "${holder.parent.context.getString(R.string.nominal)} ${data.balance}"
        holder.text?.text = data.cardName
        holder.cardView?.setOnClickListener { viewSetClickListener?.invoke(data) }
    }

    override fun getItemCount(): Int = data.size

}