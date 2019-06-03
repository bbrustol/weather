package com.brustoloni.weather.data.di

import com.brustoloni.weather.BuildConfig
import org.koin.dsl.module.module

val urlModule = module {
    factory { BuildConfig.BASE_URL }
}