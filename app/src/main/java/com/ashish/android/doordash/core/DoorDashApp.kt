package com.ashish.android.doordash.core

import android.app.Application
import com.ashish.android.doordash.core.di.AppComponent
import com.ashish.android.doordash.core.di.AppModule

class DoorDashApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)
    }
}