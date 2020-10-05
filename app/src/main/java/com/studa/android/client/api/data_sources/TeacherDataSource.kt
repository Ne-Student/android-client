package com.studa.android.client.api.data_sources

import com.studa.android.client.api.Response
import com.studa.android.client.api.dto.TeacherDTO
import io.reactivex.rxjava3.core.Single
import java.util.*

interface TeacherDataSource {
    fun createTeacher(teacher: TeacherDTO): Single<Response<TeacherDTO>>
    fun getTeacherById(id: UUID): Single<Response<TeacherDTO>>
    fun updateTeacher(teacher: TeacherDTO): Single<Response<Unit>>
    fun deleteTeacherById(id: UUID): Single<Response<Unit>>
    fun getAllAccessibleTeachers(): Single<Response<List<TeacherDTO>>>
}