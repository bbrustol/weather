package com.brustoloni.weather.data.entity.weather


import com.squareup.moshi.Json

data class Flags(
    @Json(name = "meteoalarm-license")
    val meteoalarmLicense: String?,
    @Json(name = "nearest-station")
    val nearestStation: Double?,
    val sources: List<String?>?,
    val units: String?
)