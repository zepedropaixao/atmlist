package me.paixao.atmlist

import android.app.Application
import me.paixao.atmlist.di.AppModule
import me.paixao.atmlist.di.DaggerNetComponent
import me.paixao.atmlist.di.NetComponent
import me.paixao.atmlist.di.NetModule
import timber.log.Timber
import timber.log.Timber.DebugTree



class AppATMList : Application() {

    companion object {
        lateinit var instance: AppATMList
            private set

        lateinit var netComponent: NetComponent
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        netComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(NetModule("http://207.154.210.145:8080/data/"))
                .build()

        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)*/
    }


}