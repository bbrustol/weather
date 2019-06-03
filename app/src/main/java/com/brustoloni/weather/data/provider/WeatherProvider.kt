package com.brustoloni.weather.data.provider

import com.brustoloni.weather.data.infraestructure.WeatherService
import com.brustoloni.weather.data.infraestructure.callAsync

class WeatherProvider(private val service: WeatherService) {

    suspend fun fetchWeather(latLon: String, units: String, excludes: String) =
        callAsync { service.fetchWeatherAsync(latLon, units, excludes) }.await()
}