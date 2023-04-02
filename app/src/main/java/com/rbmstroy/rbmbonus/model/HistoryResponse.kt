package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HistoryResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("items") val items: List<History>
) : Serializable {

    companion object {

    }
}