package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("message") val message: String,
    @SerializedName("statusPhone") val statusPhone: String
    ) : Serializable