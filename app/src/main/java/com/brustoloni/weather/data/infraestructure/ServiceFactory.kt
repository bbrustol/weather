package com.brustoloni.weather.data.infraestructure

import retrofit2.Retrofit

class ServiceFactory(private val retrofit: Retrofit){

    fun weatherService(): WeatherService = retrofit.create(WeatherService::class.java)
}