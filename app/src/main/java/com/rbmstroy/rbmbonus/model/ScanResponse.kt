package com.rbmstroy.rbmbonus.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ScanResponse(
    @SerializedName("message") val message: String,
    @SerializedName("errtext") val errtext: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("prodName") val prodName: String,
    @SerializedName("balance") val balance: String
) : Serializable