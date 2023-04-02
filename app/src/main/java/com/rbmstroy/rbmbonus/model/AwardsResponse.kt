package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AwardsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("items") val items: List<Awards>
) : Serializable {

    companion object {

    }
}