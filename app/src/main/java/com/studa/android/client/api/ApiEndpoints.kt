package com.studa.android.client.api

import com.studa.android.client.api.dto.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import java.util.*

interface ApiEndpoints {
    // Authentication endpoints

    @POST("auth/login")
    fun loginUser(@Body authData: AuthenticationDataDTO): Single<WeakApiResponse<AccessTokenDTO>>

    @POST("auth/register")
    fun registerUser(@Body registerData: RegisterDataDTO): Single<WeakApiResponse<AccessTokenDTO>>


    // Teacher endpoints

    @PUT("teacher")
    fun createTeacher(@Body teacher: TeacherDTO): Single<WeakApiResponse<TeacherDTO>>

    @GET("teacher/{id}")
    fun getTeacherById(@Path("id") id: UUID): Single<WeakApiResponse<TeacherDTO>>

    @PATCH("teacher/{id}")
    fun updateTeacher(@Path("id") id: UUID, @Body teacher: TeacherDTO): Completable

    @DELETE("teacher/{id}")
    fun deleteTeacher(@Path("id") id: UUID): Completable

    @GET("teachers")
    fun getAllAccessibleTeachers(): Single<WeakApiResponse<List<TeacherDTO>>>


    // Lesson endpoints

    @PUT("lesson")
    fun createLesson(@Body lesson: LessonDTO): Single<WeakApiResponse<LessonDTO>>

    @GET("lesson/{id}")
    fun getLessonById(@Path("id") id: UUID): Single<WeakApiResponse<LessonDTO>>

    @PATCH("lesson/{id}")
    fun updateLesson(@Path("id") id: UUID, @Body lesson: LessonDTO): Completable

    @DELETE("lesson/{id}")
    fun deleteLesson(@Path("id") id: UUID): Completable

    @GET("lessons")
    fun getAllLessonsForDate(@Query("date") dateString: String): Single<WeakApiResponse<List<LessonDTO>>>
}