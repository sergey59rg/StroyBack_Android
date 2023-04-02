package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BuyResponse(
    @SerializedName("items") val items: List<Buy>,
    @SerializedName("text") val text: String,
) : Serializable {

    companion object {

    }
}