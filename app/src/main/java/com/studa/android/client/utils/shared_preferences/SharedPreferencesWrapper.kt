package com.studa.android.client.utils.shared_preferences

interface SharedPreferencesWrapper {
    fun saveAccessToken(accessToken: String)
    fun getAccessToken(): String?
}