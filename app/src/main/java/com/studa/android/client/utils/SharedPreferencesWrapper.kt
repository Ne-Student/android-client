package com.studa.android.client.utils

import android.app.Activity
import android.content.Context
import com.studa.android.client.api.model.AccessToken


private const val ACCESS_TOKEN = "access_token"
private const val SHARED_PREFERENCES = "access_token"


fun saveAccessToken(context: Context, token: AccessToken) {
    val editor = context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE).edit()

    editor.putString(ACCESS_TOKEN, token.accessToken)
        .apply()
}

fun getAccessToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE)
    return sharedPreferences.getString(ACCESS_TOKEN, null)
}
