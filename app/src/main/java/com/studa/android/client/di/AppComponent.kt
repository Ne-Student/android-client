package com.studa.android.client.di

import com.studa.android.client.di.modules.NetworkModule
import com.studa.android.client.view.LauncherActivity
import com.studa.android.client.view.today.TodayFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    // Activities
    fun inject(launcherActivity: LauncherActivity)

    // Fragments
    fun inject(todayFragment: TodayFragment)
}