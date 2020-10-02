package com.studa.android.client.api.network_wrapper

import android.content.Context
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import com.studa.android.client.api.ApiEndpoints
import com.studa.android.client.utils.shared_preferences.SharedPreferencesWrapper
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


private const val BASE_URL = "http://10.0.2.2:4010/"
private const val TAG = "Repository"

@Singleton
class NetworkWrapperImpl
@Inject constructor(
    private val sharedPreferencesWrapper: SharedPreferencesWrapper
) : NetworkWrapper {
    private val retrofit: Retrofit
    private val api: ApiEndpoints

    private var accessToken: String? = null

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

    override fun getApi(): ApiEndpoints = api

    override fun getAccessToken(): String? = accessToken

    override fun saveAccessToken(accessToken: String) {
        this.accessToken = accessToken
        sharedPreferencesWrapper.saveAccessToken(accessToken)
    }

    inner class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val newRequestBuilder = chain.request().newBuilder()

            accessToken?.let {
                newRequestBuilder.addHeader("Authorization", "Bearer $it")
            }

            val newRequest = newRequestBuilder.build()
            return chain.proceed(newRequest)
        }
    }
}