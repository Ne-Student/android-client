package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName

data class RegisterDataDTO(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val login: String,
    val password: String
)