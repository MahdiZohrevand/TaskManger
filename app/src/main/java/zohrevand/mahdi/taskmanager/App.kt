package zohrevand.mahdi.taskmanager

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import zohrevand.mahdi.taskmanager.di.mainModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //for debugging
        Stetho.initializeWithDefaults(this);

        AndroidThreeTen.init(this)
        //koin module
        val moduleList = listOf(mainModule)
        startKoin {
            androidContext(this@App)
            modules(moduleList)
        }
        //timeber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}