package com.studa.android.client.api

import com.studa.android.client.api.model.AccessToken
import com.studa.android.client.api.model.AuthenticationData
import com.studa.android.client.api.model.RegisterData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiEndpoints {

    @POST("auth/login")
    fun loginUser(@Body authData: AuthenticationData): Single<WeakApiResponse<AccessToken>>

    @POST("auth/register")
    fun registerUser(@Body registerData: RegisterData): Single<WeakApiResponse<AccessToken>>

}