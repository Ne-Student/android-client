package com.nestudent.android.myapplication.api

data class ApiResponse<T>(
    val payload: T? = null,
    val error: String? = null
)