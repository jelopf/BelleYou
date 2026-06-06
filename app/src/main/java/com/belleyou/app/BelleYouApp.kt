package com.belleyou.app

import android.app.Application
import com.belleyou.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BelleYouApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BelleYouApp)
            modules(appModule)
        }
    }
}