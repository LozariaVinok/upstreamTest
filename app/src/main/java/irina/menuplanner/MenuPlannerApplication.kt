package irina.menuplanner

import android.app.Application
import irina.menuplanner.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MenuPlannerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //ComponentContainer.buildComponent(this)

        startKoin {
            // declare used Android context
            androidContext(this@MenuPlannerApplication)
            // declare modules
            modules(myModule)
        }
    }
}