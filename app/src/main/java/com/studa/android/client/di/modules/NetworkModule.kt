package com.studa.android.client.di.modules

import com.studa.android.client.api.Repository
import com.studa.android.client.api.services.auth.AuthenticationService
import com.studa.android.client.api.services.auth.AuthenticationServiceImpl
import com.studa.android.client.api.services.teacher.TeacherService
import com.studa.android.client.api.services.teacher.TeacherServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun getMainRepository() = Repository()

    @Provides
    fun getTeacherService(repository: Repository): TeacherService =
        TeacherServiceImpl(repository.api)

    @Provides
    fun getAuthenticationService(repository: Repository): AuthenticationService =
        AuthenticationServiceImpl(repository.api, repository)
}