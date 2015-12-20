package fr.xebia.xwhois

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class XwhoisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }
}
