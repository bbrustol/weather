package com.brustoloni.weather.di

import com.brustoloni.weather.data.infraestructure.UI
import com.brustoloni.weather.presentation.weather.WeatherViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel { WeatherViewModel(get(), get(), UI) }
}

