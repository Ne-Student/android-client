package com.studa.android.client.api.dto

import java.util.*

data class Lesson(
    var id: UUID? = null,
    var title: String? = null,
    var description: String? = null,
    var teachers: List<Teacher>? = null,
    var repeats: List<Repeat>? = null,
    var singles: List<Single>? = null
)