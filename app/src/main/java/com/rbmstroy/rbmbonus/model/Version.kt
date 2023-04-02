package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Version(
    @SerializedName("last_version") val last_version: String,
    @SerializedName("update_recommended") val update_recommended: Boolean,
    @SerializedName("update_required") val update_required: Boolean
) : Serializable