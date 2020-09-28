package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token")
    var accessToken: String? = null
)