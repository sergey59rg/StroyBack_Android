package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserResponse(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("balance") val balance: Int,
    @SerializedName("requedCards") val requedCards: String,
    @SerializedName("personalAccount") val personalAccount: String,
    @SerializedName("message") val message: String
) : Serializable