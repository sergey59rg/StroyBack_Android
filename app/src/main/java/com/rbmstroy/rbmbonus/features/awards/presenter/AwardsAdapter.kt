package com.rbmstroy.rbmbonus.features.awards.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.Awards
import com.squareup.picasso.Picasso

class AwardsAdapter(var data: List<Awards>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {

    }

    fun add(data: List<Awards>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.awards_item, parent, false)
        return AwardsViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        holder as AwardsViewHolder
        if (data.img.isNotEmpty()) {
            Picasso.with(holder.image?.context).load(data.img).into(holder.image)
        }
        holder.image?.setClipToOutline(true)
        holder.price?.text = "${data.price} ${holder.parent.context.getString(R.string.rub)}"
        holder.text?.text = data.name
    }

    override fun getItemCount(): Int = data.size

}