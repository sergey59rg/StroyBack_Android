package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Awards(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("brand") val brand: String,
    @SerializedName("img") val img: String
) : Serializable