package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VersionResponse(
    @SerializedName("android") val android: Version,
    @SerializedName("ios") val ios: Version
) : Serializable {

    companion object {

    }
}