package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName

data class WeeklyDTO(
    var at: String?,

    @SerializedName("end_date")
    var endDate: String?,

    var day: Int?,
    var every: Int?,

    @SerializedName("start_date")
    var startDate: String?
)