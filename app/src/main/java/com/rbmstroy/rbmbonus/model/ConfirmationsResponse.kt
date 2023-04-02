package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ConfirmationsResponse(
    @SerializedName("items") val items: List<Confirmations>
) : Serializable {

    companion object {

    }
}