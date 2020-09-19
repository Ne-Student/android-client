package com.studa.android.client.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.*

// General purpose api error DTO, contains ALL fields for ALL types of possible errors
data class WeakApiError(
    var type: String?,
    val message: String?,
    @SerializedName("in")
    val _in: String?,
    @SerializedName("entity_id")
    val entityId: UUID?,
    @SerializedName("entity_type")
    val entityType: String?
)

// Generified api response, should be parsed into Ok(payload) or api specific error
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
    data class Ok<T>(val payload: T? = null, val msg: String? = null) : Response<T>()

    sealed class Error<out T> : Response<T>() {
        data class UnresolvedApiError<T>(val msg: String? = null) : Error<T>()
        // api specific errors
        object NetworkError : Error<Nothing>()
        object NoTokenPresentError: Error<Nothing>()
        data class InvalidCredentialsError<T>(val msg: String? = null) : Error<T>()
        data class LoginAlreadyPresentError<T>(val msg: String? = null) : Error<T>()
        data class NoReadAccessError<T>(val entityId: UUID? = null, val entityType: String? = null): Error<T>()
        data class NoWriteAccessError<T>(val entityId: UUID? = null, val entityType: String? = null): Error<T>()
        object TeacherDoesNotExistError: Error<Nothing>()
        data class BadRequestError<T>(val _in: String? = null, val message: String? = null): Error<T>()
        object InternalServerError: Error<Nothing>()

        companion object {
            inline fun <reified T> fromWeakApiError(weakError: WeakApiError): Error<T> {
                val (type, msg, _in, entityId, entityType) = weakError
                return when (type) {
                    ApiErrorDefinitions.NO_TOKEN_PRESENT -> NoTokenPresentError
                    ApiErrorDefinitions.INVALID_CREDENTIALS -> InvalidCredentialsError(msg)
                    ApiErrorDefinitions.LOGIN_ALREADY_PRESENT -> LoginAlreadyPresentError(msg)
                    ApiErrorDefinitions.NO_READ_ACCESS -> NoReadAccessError(entityId, entityType)
                    ApiErrorDefinitions.NO_WRITE_ACCESS -> NoWriteAccessError(entityId, entityType)
                    ApiErrorDefinitions.TEACHER_DOES_NOT_EXIST -> TeacherDoesNotExistError
                    ApiErrorDefinitions.BAD_REQUEST -> BadRequestError(_in, msg)
                    ApiErrorDefinitions.INTERNAL_ERROR -> InternalServerError
                    else -> UnresolvedApiError(msg)
                }
            }
        }
    }

    companion object {
        // try to cast http error codes (300+) into api specific error
        inline fun <reified T> errorFromWeakApiResponse(weakResponse: WeakApiResponse<T>)
                : Error<T> = when {
            weakResponse.error != null -> Error.fromWeakApiError(weakResponse.error)
            else -> Error.UnresolvedApiError()
        }

        // try to cast http 200 codes with body into ok or api specific error
        inline fun <reified T> okFromWeakApiResponse(weakResponse: WeakApiResponse<T>)
                : Response<T> = when {
            weakResponse.error != null -> Error.fromWeakApiError(weakResponse.error)
            weakResponse.payload != null -> Ok(weakResponse.payload)
            else -> Error.UnresolvedApiError()
        }
    }
}