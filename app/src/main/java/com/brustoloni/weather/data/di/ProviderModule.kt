package com.brustoloni.weather.data.di

import com.brustoloni.weather.data.provider.WeatherProvider
import org.koin.dsl.module.module

val provideModule = module(override = true) {
    factory { WeatherProvider(get()) }
}