package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class History(
    @SerializedName("name") val name: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("dataEvent") val dataEvent: String,
    @SerializedName("imgIndex") val imgIndex: String
) : Serializable