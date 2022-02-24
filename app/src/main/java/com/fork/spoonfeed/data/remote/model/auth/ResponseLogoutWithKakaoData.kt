package com.fork.spoonfeed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class ResponseLogoutWithKakaoData(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: Data
) {
    data class Data(
        @SerializedName("id") val id: String
    )
}