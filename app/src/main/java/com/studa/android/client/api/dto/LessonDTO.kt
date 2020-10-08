package com.studa.android.client.api.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class LessonDTO(
    var id: UUID?,
    var title: String?,
    var description: String?,
    @SerializedName("teachers")
    var teachersIds: List<UUID>?,
    var singles: List<String>?,
    var daily: List<DailyDTO>?,
    var monthly: List<MonthlyDTO>?,
    var weekly: List<WeeklyDTO>?
)