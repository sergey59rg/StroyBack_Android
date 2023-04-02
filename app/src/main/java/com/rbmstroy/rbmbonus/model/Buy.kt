package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Buy(
    @SerializedName("name") val name: String,
    @SerializedName("img") val img: String,
    @SerializedName("nominal") val nominal: String,
    @SerializedName("imgid") val imgid: Int
) : Serializable