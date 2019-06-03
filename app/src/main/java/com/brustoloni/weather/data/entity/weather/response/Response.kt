package com.brustoloni.weather.data.entity.weather.response

import com.brustoloni.weather.data.entity.weather.*

data class WeatherResponse(
    val alerts: List<Alert?>?,
    val currently: Currently?,
    val daily: Daily?,
    val flags: Flags?,
    val hourly: Hourly?,
    val latitude: Double?,
    val longitude: Double?,
    val offset: Int?,
    val timeSummary: String?,
    val timezone: String?
)

data class FormattedWeatherResponse(
    val icon: Int,
    val temperature: String = "",
    val summary: String = "",
    val timezone: String = "",
    val time: String = "",
    val key: String = "",
    val value: String = "",
    val weekOfDay: String = "",
    val temperatureMin: String = "",
    val temperatureMax: String = "",
    val type: Int = 0,
    val hourly: ArrayList<FormattedWeatherHourly> = arrayListOf()
)
