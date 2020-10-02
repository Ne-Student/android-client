package com.studa.android.client.di_modules

import android.content.Context
import com.studa.android.client.utils.shared_preferences.SharedPreferencesWrapper
import com.studa.android.client.utils.shared_preferences.SharedPreferencesWrapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LocalStorageModule {
    @Provides
    @Singleton
    fun getSharedPreferencesWrapper(@ApplicationContext appContext: Context):
            SharedPreferencesWrapper = SharedPreferencesWrapperImpl(appContext)
}