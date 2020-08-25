package com.nestudent.android.myapplication.utils

import android.app.Activity
import android.content.Context
import com.nestudent.android.myapplication.api.model.AccessToken


private const val ACCESS_TOKEN = "access_token"
private const val SHARED_PREFERENCES = "access_token"

class SharedPreferencesWrapper {
    companion object {
        fun saveAccessToken(context: Context, token: AccessToken) {
            val editor = context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE).edit()

            editor.putString(ACCESS_TOKEN, token.accessToken)
                .apply()
        }

        fun getAccessToken(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE)
            return sharedPreferences.getString(ACCESS_TOKEN, null)
        }
    }
}