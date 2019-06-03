package com.brustoloni.weather.data.infraestructure

import com.brustoloni.weather.data.entity.weather.response.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("{latLon}")
    fun fetchWeatherAsync(@Path(value = "latLon") latLon: String,
                          @Query("units") units: String,
                          @Query("exclude") exclude: String): Deferred<Response<WeatherResponse>>
}