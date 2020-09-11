package com.studa.android.client.api.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token")
    var accessToken: String? = null
)