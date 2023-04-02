package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateResponse(
    @SerializedName("message") val message: String
) : Serializable