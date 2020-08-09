package com.nestudent.android.myapplication.api

import com.nestudent.android.myapplication.api.model.AccessToken
import com.nestudent.android.myapplication.api.model.AuthenticationData
import com.nestudent.android.myapplication.api.model.RegisterData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiEndpoindts {

    @POST("auth/login")
    fun loginUser(@Body authData: AuthenticationData): Single<ApiResponse<AccessToken>>

    @POST("auth/register")
    fun registerUser(@Body registerData: RegisterData): Single<ApiResponse<AccessToken>>

}