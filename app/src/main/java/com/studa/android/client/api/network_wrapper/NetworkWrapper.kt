package com.studa.android.client.api.network_wrapper

import com.studa.android.client.api.ApiEndpoints

interface NetworkWrapper {
    fun getApi(): ApiEndpoints
    fun getAccessToken(): String?
    fun saveAccessToken(accessToken: String)
}