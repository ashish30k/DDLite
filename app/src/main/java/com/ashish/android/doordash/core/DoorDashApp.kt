package com.ashish.android.doordash.core

import android.app.Application

class DoorDashApp: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}