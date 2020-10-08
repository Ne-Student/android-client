package com.studa.android.client.api.data_sources

import com.studa.android.client.api.Response
import com.studa.android.client.api.dto.LessonDTO
import com.studa.android.client.api.dto.TeacherDTO
import io.reactivex.rxjava3.core.Single
import java.util.*

interface LessonDataSource {
    fun createLesson(lesson: LessonDTO): Single<Response<LessonDTO>>
    fun getLessonById(id: UUID): Single<Response<LessonDTO>>
    fun updateLesson(lesson: LessonDTO): Single<Response<Unit>>
    fun deleteLessonById(id: UUID): Single<Response<Unit>>
    fun getAllLessonsForDate(date: String): Single<Response<List<LessonDTO>>>
}