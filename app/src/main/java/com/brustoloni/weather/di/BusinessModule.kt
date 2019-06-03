package com.brustoloni.weather.di

import com.brustoloni.weather.business.WeatherBusiness
import org.koin.dsl.module.module

val businessModule = module {

    factory { WeatherBusiness(get()) }
}
