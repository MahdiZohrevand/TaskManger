package zohrevand.mahdi.taskmanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import zohrevand.mahdi.taskmanager.di.mainModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val moduleList = listOf(mainModule)
        startKoin {
            androidContext(this@App)
            modules(moduleList)
        }
    }
}