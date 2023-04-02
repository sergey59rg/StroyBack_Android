package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notifications(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("message") val message: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("isRead") val isRead: Int
) : Serializable