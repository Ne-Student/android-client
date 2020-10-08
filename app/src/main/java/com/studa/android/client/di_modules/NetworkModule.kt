package com.studa.android.client.di_modules

import com.studa.android.client.api.data_sources.AuthenticationDataSource
import com.studa.android.client.api.data_sources.LessonDataSource
import com.studa.android.client.api.data_sources.impl.AuthenticationDataSourceImpl
import com.studa.android.client.api.data_sources.TeacherDataSource
import com.studa.android.client.api.data_sources.impl.LessonDataSourceImpl
import com.studa.android.client.api.data_sources.impl.TeacherDataSourceImpl
import com.studa.android.client.api.network_wrapper.NetworkWrapper
import com.studa.android.client.api.network_wrapper.NetworkWrapperImpl
import com.studa.android.client.utils.shared_preferences.SharedPreferencesWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun getNetworkWrapper(sharedPreferencesWrapper: SharedPreferencesWrapper):
        NetworkWrapper = NetworkWrapperImpl(sharedPreferencesWrapper)

    @Provides
    fun getTeacherService(networkWrapper: NetworkWrapper): TeacherDataSource =
        TeacherDataSourceImpl(networkWrapper)

    @Provides
    fun getAuthenticationService(networkWrapper: NetworkWrapper): AuthenticationDataSource =
        AuthenticationDataSourceImpl(networkWrapper)

    @Provides
    fun getLessonService(networkWrapper: NetworkWrapper): LessonDataSource =
        LessonDataSourceImpl(networkWrapper)
}