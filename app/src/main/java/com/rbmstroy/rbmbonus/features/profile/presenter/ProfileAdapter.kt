package com.rbmstroy.rbmbonus.features.profile.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.Profile

class ProfileAdapter(var data: List<Profile>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewSetClickListener: ((Profile) ->Unit)? = null

    init {

    }

    fun add(data: List<Profile>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        return ProfileViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        holder as ProfileViewHolder
        holder.image?.setImageResource(data.img)
        holder.image?.setClipToOutline(true)
        holder.text?.text = data.text
        holder.cardView?.setOnClickListener { viewSetClickListener?.invoke(data) }
    }

    override fun getItemCount(): Int = data.size

}