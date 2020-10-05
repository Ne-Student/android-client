package com.studa.android.client.api.data_sources

import com.studa.android.client.api.Response
import com.studa.android.client.api.dto.AccessTokenDTO
import com.studa.android.client.api.dto.AuthenticationDataDTO
import com.studa.android.client.api.dto.RegisterDataDTO
import io.reactivex.rxjava3.core.Single

interface AuthenticationDataSource {
    fun registerUser(registerData: RegisterDataDTO): Single<Response<AccessTokenDTO>>
    fun loginUser(authData: AuthenticationDataDTO): Single<Response<AccessTokenDTO>>
}