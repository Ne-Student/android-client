package com.studa.android.client.api.services.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.studa.android.client.api.ApiEndpoints
import com.studa.android.client.api.Repository
import com.studa.android.client.api.Response
import com.studa.android.client.api.WeakApiResponse
import com.studa.android.client.api.dto.AccessToken
import com.studa.android.client.api.dto.AuthenticationData
import com.studa.android.client.api.dto.RegisterData
import com.studa.android.client.utils.defaultErrorHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class AuthenticationServiceImpl @Inject constructor(
    val api: ApiEndpoints, val repository: Repository
) : AuthenticationService {
    private fun getAccessTokenObservableSubscriber(
        result: MutableLiveData<Response<AccessToken>>
    ) = object : SingleObserver<WeakApiResponse<AccessToken>> {
        override fun onSuccess(response: WeakApiResponse<AccessToken>) =
            if (response.payload?.accessToken != null) {
                repository.accessToken = response.payload.accessToken
                result.value = Response.Ok(response.payload)
            } else {
                result.value = Response.Error.UnresolvedApiError()
            }

        override fun onError(error: Throwable) {
            result.value = defaultErrorHandler(error)
        }

        override fun onSubscribe(d: Disposable) {}
    }

    override fun registerUser(registerData: RegisterData): LiveData<Response<AccessToken>> {
        val result: MutableLiveData<Response<AccessToken>> = MutableLiveData()
        api.registerUser(registerData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getAccessTokenObservableSubscriber(result))

        return result
    }

    override fun loginUser(authData: AuthenticationData): LiveData<Response<AccessToken>> {
        val result: MutableLiveData<Response<AccessToken>> = MutableLiveData()
        api.loginUser(authData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getAccessTokenObservableSubscriber(result))
        return result
    }
}