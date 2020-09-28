package com.studa.android.client.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.studa.android.client.api.model.*
import com.studa.android.client.utils.defaultErrorHandler
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
import java.util.*
import javax.inject.Singleton


private const val BASE_URL = "http://10.0.2.2:4010/"
private const val TAG = "Repository"

// TODO: Refactor this class, potentially just delete it
class Repository {
    private val retrofit: Retrofit
    val api: ApiEndpoints

    var accessToken: String? = null

    init {
        val gson = Converters.registerDateTime(GsonBuilder()).create()

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        retrofit = Retrofit
            .Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(ApiEndpoints::class.java)
    }

    inner class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val newRequestBuilder = chain.request().newBuilder()

            if (accessToken != null)
                newRequestBuilder.addHeader("Authorization", "Bearer $accessToken")

            val newRequest = newRequestBuilder
                //.addHeader("Prefer", "code=500") // TODO: disable on backend ready
                .build()
            return chain.proceed(newRequest)
        }
    }
}