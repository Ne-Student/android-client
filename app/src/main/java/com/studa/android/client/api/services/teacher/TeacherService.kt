package com.studa.android.client.api.services.teacher

import androidx.lifecycle.LiveData
import com.studa.android.client.api.Response
import com.studa.android.client.api.dto.Teacher
import java.util.*

interface TeacherService {
    fun createTeacher(teacher: Teacher): LiveData<Response<Teacher>>
    fun getTeacherById(id: UUID): LiveData<Response<Teacher>>
    fun updateTeacher(teacher: Teacher): LiveData<Response<Unit>>
    fun deleteTeacherById(id: UUID): LiveData<Response<Unit>>
    fun getAllAccessibleTeachers(): LiveData<Response<List<Teacher>>>
}