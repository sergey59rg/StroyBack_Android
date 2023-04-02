package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Promotion(
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("img") val img: String,
    @SerializedName("backgroundimg") val backgroundimg: String,
    @SerializedName("url") val url: String,
    @SerializedName("product_id") val product_id: String
) : Serializable