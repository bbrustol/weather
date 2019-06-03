package com.brustoloni.weather.business

import com.brustoloni.weather.data.entity.weather.response.WeatherResponse
import com.brustoloni.weather.data.infraestructure.Failure
import com.brustoloni.weather.data.infraestructure.Resource
import com.brustoloni.weather.data.infraestructure.Success
import com.brustoloni.weather.data.provider.WeatherProvider

class WeatherBusiness(private val weatherProvider: WeatherProvider) {

    suspend fun fetchWeather(latLon: String, units: String, excludes: String): Resource<WeatherResponse> {

        return when (val response: Resource<WeatherResponse> = weatherProvider.fetchWeather(latLon, units, excludes)) {
            is Success -> Success(response.data)
            is Failure -> Failure(response.data, response.networkState)
        }
    }
}