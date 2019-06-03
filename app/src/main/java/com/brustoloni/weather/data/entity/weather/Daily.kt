package com.brustoloni.weather.data.entity.weather


data class Daily(
    val `data`: List<DataX?>?,
    val icon: String?,
    val summary: String?
)