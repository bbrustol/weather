package com.brustoloni.weather.data.di

import com.brustoloni.weather.data.infraestructure.ServiceFactory
import org.koin.dsl.module.module

val serviceModule = module(override = true) {
    factory { ServiceFactory(get()).weatherService() }
}
