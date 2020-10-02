package com.studa.android.client.api.data_sources

import com.studa.android.client.api.Response
import com.studa.android.client.api.dto.AccessToken
import com.studa.android.client.api.dto.AuthenticationData
import com.studa.android.client.api.dto.RegisterData
import io.reactivex.rxjava3.core.Single

interface AuthenticationDataSource {
    fun registerUser(registerData: RegisterData): Single<Response<AccessToken>>
    fun loginUser(authData: AuthenticationData): Single<Response<AccessToken>>
}