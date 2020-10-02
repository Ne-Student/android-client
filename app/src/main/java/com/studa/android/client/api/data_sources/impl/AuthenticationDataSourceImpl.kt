package com.studa.android.client.api.data_sources.impl

import com.studa.android.client.api.Response
import com.studa.android.client.api.WeakApiResponse
import com.studa.android.client.api.data_sources.AuthenticationDataSource
import com.studa.android.client.api.dto.AccessToken
import com.studa.android.client.api.dto.AuthenticationData
import com.studa.android.client.api.dto.RegisterData
import com.studa.android.client.api.network_wrapper.NetworkWrapper
import com.studa.android.client.utils.defaultErrorHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class AuthenticationDataSourceImpl @Inject constructor(
   val networkWrapper: NetworkWrapper
) : AuthenticationDataSource {
    private fun getAccessTokenObservableSubscriber(
        emitter: SingleEmitter<Response<AccessToken>>
    ) = object : SingleObserver<WeakApiResponse<AccessToken>> {
        override fun onSuccess(response: WeakApiResponse<AccessToken>) =
            if (response.payload?.accessToken != null) {
                // TODO: revisit this code
                response.payload.accessToken?.let {
                    networkWrapper.saveAccessToken(it)
                }
                emitter.onSuccess(Response.Ok(response.payload))
            } else {
                emitter.onSuccess(Response.Error.UnresolvedApiError())
            }

        override fun onError(error: Throwable) =
            emitter.onSuccess(defaultErrorHandler(error))

        override fun onSubscribe(d: Disposable) = Unit
    }

    override fun registerUser(registerData: RegisterData): Single<Response<AccessToken>> =
        Single.create { emitter ->
            networkWrapper.getApi().registerUser(registerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAccessTokenObservableSubscriber(emitter))
        }

    override fun loginUser(authData: AuthenticationData): Single<Response<AccessToken>> =
        Single.create { emitter ->
            networkWrapper.getApi().loginUser(authData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAccessTokenObservableSubscriber(emitter))
        }
}