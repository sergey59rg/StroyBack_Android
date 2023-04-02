package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Confirmations(
    @SerializedName("cardName") val cardName: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("imgId") val imgId: String,
    @SerializedName("img") val img: String
) : Serializable