package com.nestudent.android.myapplication.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nestudent.android.myapplication.api.model.AccessToken
import com.nestudent.android.myapplication.api.model.AuthenticationData
import com.nestudent.android.myapplication.api.model.RegisterData
import com.nestudent.android.myapplication.utils.SharedPreferencesWrapper
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


private const val BASE_URL = "http://10.0.2.2:4010/"
private const val TAG = "Repository"

class Repository {
    private val retrofit: Retrofit
    val api: ApiEndpoints

    var accessToken: String? = null

    init {
        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        retrofit = Retrofit
            .Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiEndpoints::class.java)
    }

    private fun getAccessTokenObservableSubscriber(
        result: MutableLiveData<Response<AccessToken>>
    ) = object : SingleObserver<WeakApiResponse<AccessToken>> {
        override fun onSuccess(response: WeakApiResponse<AccessToken>) {
            if (response.payload?.accessToken != null) {
                accessToken = response.payload.accessToken
                result.value = Response.Ok(response.payload)
            } else {
                result.value = Response.Error.UnresolvedApiError()
            }
        }

        override fun onError(error: Throwable) {
            when (error) {
                is HttpException -> {
                    val apiError = WeakApiResponse.fromJson<AccessToken>(
                        error.response()?.errorBody()?.string() ?: ""
                    )
                    result.value = if (apiError != null) {
                        Response.fromWeakApiResponse(apiError)
                    } else {
                        Response.Error.NetworkError
                    }
                }
                else -> result.value = Response.Error.NetworkError
            }
        }

        override fun onSubscribe(d: Disposable) {}
    }

    fun registerUser(registerData: RegisterData)
            : LiveData<Response<AccessToken>> {
        val result: MutableLiveData<Response<AccessToken>> = MutableLiveData()
        api.registerUser(registerData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getAccessTokenObservableSubscriber(result))

        return result
    }

    fun loginUser(authData: AuthenticationData): LiveData<Response<AccessToken>> {
        val result: MutableLiveData<Response<AccessToken>> = MutableLiveData()
        api.loginUser(authData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getAccessTokenObservableSubscriber(result))
        return result
    }

    inner class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val newRequestBuilder = chain.request().newBuilder()

            if (accessToken != null)
                newRequestBuilder.addHeader("Authorization", "Bearer $accessToken")

            val newRequest = newRequestBuilder
                //.addHeader("Prefer", "code=401") // TODO: disable on backend ready
                .build()
            return chain.proceed(newRequest)
        }
    }

    companion object {
        private val _instance: Repository by lazy {
            Repository()
        }
        val instance: Repository
            get() = _instance
    }
}