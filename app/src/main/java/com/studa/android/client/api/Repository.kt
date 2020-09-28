package com.studa.android.client.api

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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