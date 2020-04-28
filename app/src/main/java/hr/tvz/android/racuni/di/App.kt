package hr.tvz.android.racuni.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            AndroidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(roomModule, repositoryModule, viewModelModule))
        }
    }
}