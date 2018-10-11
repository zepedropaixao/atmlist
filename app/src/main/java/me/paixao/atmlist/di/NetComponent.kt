package me.paixao.atmlist.di

import android.app.Application
import dagger.Component
import me.paixao.atmlist.ui.MainActViewModel
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface NetComponent {
    fun inject(mrds: MainActViewModel)

    fun app(): Application

    fun retrofit(): Retrofit
}