package com.akshit.onebanc.infra

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CoreApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var appContext: CoreApplication
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}