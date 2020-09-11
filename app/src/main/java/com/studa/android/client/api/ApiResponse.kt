package com.studa.android.client.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class WeakApiError(
    var type: String? = null,
    val message: String? = null
)

data class WeakApiResponse<T>(
    val payload: T? = null,
    val error: WeakApiError? = null
) {
    companion object {
        inline fun <reified T> fromJson(json: String): WeakApiResponse<T>? =
            Gson().fromJson(
                json,
                TypeToken.getParameterized(WeakApiResponse::class.java, T::class.java).type
            )
    }
}

sealed class Response<out T> {
    data class Ok<T>(val payload: T?, val msg: String? = null) : Response<T>()

    sealed class Error<out T> : Response<T>() {
        object NetworkError : Response<Nothing>()
        data class ApiError<T>(val errorType: String, val msg: String? = null) : Error<T>()
        data class InvalidCredentialsError<T>(val msg: String? = null) : Error<T>()
        data class LoginAlreadyPresentError<T>(val msg: String? = null) : Error<T>()
        data class UnresolvedApiError<T>(val msg: String? = null) : Error<T>()

        companion object {
            inline fun <reified T> fromWeakApiError(weakError: WeakApiError): Error<T> {
                val (type, msg) = weakError
                return when (type) {
                    ApiErrorDefinitions.INVALID_CREDENTIALS -> InvalidCredentialsError(msg)
                    ApiErrorDefinitions.LOGIN_ALREADY_PRESENT -> LoginAlreadyPresentError(msg)
                    else -> UnresolvedApiError(msg)
                }
            }
        }
    }

    companion object {
        inline fun <reified T> fromWeakApiResponse(weakResponse: WeakApiResponse<T>): Response<T> =
            when {
                weakResponse.error != null -> Error.fromWeakApiError(weakResponse.error)
                weakResponse.payload != null -> Ok(weakResponse.payload)
                else -> Error.NetworkError
            }
    }
}