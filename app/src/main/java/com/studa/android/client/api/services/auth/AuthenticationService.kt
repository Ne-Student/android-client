package com.studa.android.client.api.services.auth

import androidx.lifecycle.LiveData
import com.studa.android.client.api.Response
import com.studa.android.client.api.model.AccessToken
import com.studa.android.client.api.model.AuthenticationData
import com.studa.android.client.api.model.RegisterData

interface AuthenticationService {
    fun registerUser(registerData: RegisterData): LiveData<Response<AccessToken>>
    fun loginUser(authData: AuthenticationData): LiveData<Response<AccessToken>>
}