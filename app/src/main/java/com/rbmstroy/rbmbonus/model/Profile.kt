package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(
    @SerializedName("id") val id: Int,
    @SerializedName("img") val img: Int,
    @SerializedName("text") val text: String
) : Serializable