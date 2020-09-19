package com.studa.android.client.api

import com.studa.android.client.api.model.AccessToken
import com.studa.android.client.api.model.AuthenticationData
import com.studa.android.client.api.model.RegisterData
import com.studa.android.client.api.model.Teacher
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*

interface ApiEndpoints {
    // Authentication endpoints

    @POST("auth/login")
    fun loginUser(@Body authData: AuthenticationData): Single<WeakApiResponse<AccessToken>>

    @POST("auth/register")
    fun registerUser(@Body registerData: RegisterData): Single<WeakApiResponse<AccessToken>>


    // Teacher endpoints

    @PUT("teacher")
    fun createTeacher(@Body teacher: Teacher): Single<WeakApiResponse<Teacher>>

    @GET("teacher/{id}")
    fun getTeacherById(@Path("id") id: UUID): Single<WeakApiResponse<Teacher>>

    @PATCH("teacher/{id}")
    fun updateTeacher(@Path("id") id: UUID, @Body teacher: Teacher): Completable

    @DELETE("teacher/{id}")
    fun deleteTeacher(@Path("id") id: UUID): Completable

    @GET("teachers")
    fun getAllAccessibleTeachers(): Single<WeakApiResponse<List<Teacher>>>

}