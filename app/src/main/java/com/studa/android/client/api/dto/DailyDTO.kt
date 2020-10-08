package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName

data class DailyDTO(
    var at: String?,

    @SerializedName("end_date")
    var endDate: String?,

    @SerializedName("start_date")
    var startDate: String?
)