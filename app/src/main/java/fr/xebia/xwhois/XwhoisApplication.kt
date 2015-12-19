package fr.xebia.xwhois

import android.app.Application
import timber.log.Timber

class XwhoisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
