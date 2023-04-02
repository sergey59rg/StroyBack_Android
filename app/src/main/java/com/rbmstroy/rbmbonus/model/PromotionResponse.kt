package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PromotionResponse(
    @SerializedName("text") val text: String,
    @SerializedName("items") val items: List<Promotion>
) : Serializable {

    companion object {

    }
}