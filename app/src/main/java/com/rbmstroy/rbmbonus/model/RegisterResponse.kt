package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterResponse(
    @SerializedName("token") val token: String,
    @SerializedName("message") val message: String,
    @SerializedName("statusCode") val statusCode: String
) : Serializable