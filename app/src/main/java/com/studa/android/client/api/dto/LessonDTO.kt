package com.studa.android.client.api.dto

import java.util.*

data class LessonDTO(
    var id: UUID? = null,
    var title: String? = null,
    var description: String? = null,
    var teachers: List<TeacherDTO>? = null,
    var repeats: List<RepeatDTO>? = null,
    var singles: List<SingleDTO>? = null
)