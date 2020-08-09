package com.nestudent.android.myapplication.api.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token")
    var acessToken: String? = null
)