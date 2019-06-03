package com.brustoloni.weather.presentation

import android.app.Application
import com.brustoloni.weather.data.di.*
import com.brustoloni.weather.di.businessModule
import com.brustoloni.weather.di.viewModelModule
import org.koin.android.ext.android.startKoin


class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                viewModelModule,
                businessModule,
                provideModule,
                serviceModule,
                networkModule,
                urlModule,
                systemModule
            )
        )
    }
}