package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorBody(
    @SerializedName("error") val error: String
) : Serializable