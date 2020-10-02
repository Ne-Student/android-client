package com.studa.android.client.api.data_sources

import com.studa.android.client.api.Response
import com.studa.android.client.api.dto.Teacher
import io.reactivex.rxjava3.core.Single
import java.util.*

interface TeacherDataSource {
    fun createTeacher(teacher: Teacher): Single<Response<Teacher>>
    fun getTeacherById(id: UUID): Single<Response<Teacher>>
    fun updateTeacher(teacher: Teacher): Single<Response<Unit>>
    fun deleteTeacherById(id: UUID): Single<Response<Unit>>
    fun getAllAccessibleTeachers(): Single<Response<List<Teacher>>>
}