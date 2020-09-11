package com.studa.android.client.api.model

import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val login: String,
    val password: String
)