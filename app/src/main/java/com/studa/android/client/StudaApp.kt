package com.studa.android.client

import android.app.Application
import com.studa.android.client.di.DaggerAppComponent
import javax.inject.Singleton

@Singleton
class StudaApp: Application() {
    val appComponent = DaggerAppComponent.create()
}