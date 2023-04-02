package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForgottenResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String
) : Serializable