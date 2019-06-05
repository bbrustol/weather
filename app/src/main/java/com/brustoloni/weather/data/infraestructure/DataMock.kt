package com.brustoloni.weather.data.infraestructure

import com.brustoloni.weather.data.entity.weather.*
import com.brustoloni.weather.data.entity.weather.response.FormattedWeatherResponse
import com.brustoloni.weather.data.entity.weather.response.WeatherResponse

class DataMock {

    companion object {
        fun createList() = listOf(createWeather())

        fun createFormattedList() = listOf(createFormattedWeather())

        fun createFormattedWeather() = FormattedWeatherResponse(
            int, str, str, str, str, str, str, str, str, str, int
        )

        fun createWeather() = WeatherResponse(
        listOf(createAlert()),
        createCurrently(),
        createDaily(),
        null,
        createHourly(),
        double,
        double,
        0,
        "Europe/Stockholm"
        )

        fun createHourly() = Hourly(
            listOf(createData()),
            str,
            str
        )

        fun createDaily() = Daily(
            listOf(createDatax()),
            str,
            str
        )

        fun createAlert() = Alert(
            str, int, listOf(str), str, int, str, str
        )

        fun createCurrently() = Currently(
            double, double, double, double, str, double, double, double, str, double, str, double, time, int, double, int, double, double
        )

        fun createData() = Data(
            double, double, double, double, str, double, double, double, str, double, str, double, time.toInt(), int, double, int, double, double
        )

        fun createDatax() = DataX(
            double, int, double, int, double, int, double, int, double, double, double, str, double, double, double, double, int, double, str, double, str, int, int, double, int, double, int, double, int, double, int, time.toInt(), int, int, double, int, double, int, double
        )

        private const val int = 0
        private const val time: Long = 1559694188
        private const val str = ""
        const val double = 0.0
        const val ILLEGAL_ARGUMENT : String = "Illegal argument"
    }
}