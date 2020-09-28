package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.LocalTime

data class Repeat(
    var day: Int? = null,

    @SerializedName("start_date")
    var startDate: DateTime? = null,

    @SerializedName("end_date")
    var endDate: DateTime? = null,

    var every: Long? = null,

    var time: LocalTime? = null
)