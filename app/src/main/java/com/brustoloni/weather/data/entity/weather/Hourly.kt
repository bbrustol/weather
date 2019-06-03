package com.brustoloni.weather.data.entity.weather


data class Hourly(
    val `data`: List<Data?>?,
    val icon: String?,
    val summary: String?
)