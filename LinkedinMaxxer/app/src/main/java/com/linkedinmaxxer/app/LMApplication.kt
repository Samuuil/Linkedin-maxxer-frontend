package com.linkedinmaxxer.app

import android.app.Application
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.data.session.dataStore
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LMApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LMApplication)
            modules(appModule)
        }

        SessionManager.initialize(dataStore)
        runBlocking {
            SessionManager.setTokens()
        }
    }
}
