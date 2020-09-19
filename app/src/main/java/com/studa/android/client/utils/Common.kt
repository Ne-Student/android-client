package com.studa.android.client.utils

import android.content.res.Resources
import android.util.TypedValue
import com.studa.android.client.api.Response
import com.studa.android.client.api.WeakApiResponse
import retrofit2.HttpException

fun dpToInt(dp: Float, resources: Resources): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    ).toInt()

inline fun <reified T> defaultErrorHandler(error: Throwable)
        : Response.Error<T> = when (error) {
    is HttpException -> {
        var result: Response.Error<T> = Response.Error.UnresolvedApiError()
        try {
            val errorJson = error.response()?.errorBody()?.string()
            val apiError = WeakApiResponse.fromJson<T>(errorJson!!)
            result = Response.errorFromWeakApiResponse(apiError!!)
        } finally { }
        result
    }
    else -> Response.Error.NetworkError
}