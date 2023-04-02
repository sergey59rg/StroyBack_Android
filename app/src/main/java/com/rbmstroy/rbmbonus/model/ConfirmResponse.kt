package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ConfirmResponse(
    @SerializedName("message") val message: String,
    @SerializedName("Status_code") val Status_code: String
) : Serializable