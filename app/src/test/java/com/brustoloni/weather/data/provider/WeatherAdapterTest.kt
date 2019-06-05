package com.brustoloni.weather.data.provider

import com.brustoloni.weather.data.entity.weather.response.WeatherResponse
import com.brustoloni.weather.data.infraestructure.ResourceUtils
import com.squareup.moshi.Moshi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class PoiAdapterTest {

    private lateinit var moshi: Moshi

    @Before
    fun setUp(){
        moshi = Moshi.Builder()
            .build()
    }

    @Test
    fun shouldBeParse() {
        val json = ResourceUtils().openFile("weather_response_200.json")


        val adapter =
            moshi.adapter(WeatherResponse::class.java)
        val response = adapter.fromJson(json!!)

        assertNotNull(response)

        assertEquals(1.0, response?.currently?.humidity)
        assertEquals("cloudy", response?.currently?.icon)
        assertEquals(1559694188, response?.currently?.time?.toInt())
        assertEquals(13.05, response?.currently?.temperature)

        assertEquals(1014.38, response?.currently?.pressure)

        assertEquals(0.0, response?.currently?.precipIntensity)
        assertEquals(0, response?.currently?.uvIndex)
        assertEquals(10.57, response?.currently?.visibility)

    }

    @Test
    fun shouldBeReturnEmpty() {
        val json = ResourceUtils().openFile("weather_response_empty.json")

        val adapter = moshi.adapter(WeatherResponse::class.java)

        val response = adapter.fromJson(json!!)

        assertTrue(response?.timezone.isNullOrEmpty())
        response?.longitude?.isNaN()?.let { assertTrue(it) }
        response?.latitude?.isNaN()?.let { assertTrue(it) }
        assertTrue(response?.daily?.icon.isNullOrEmpty())
        assertTrue(response?.currently?.icon.isNullOrEmpty())
        assertTrue(response?.alerts.isNullOrEmpty())
        assertTrue(response?.hourly?.icon.isNullOrEmpty())
    }

    @Test
    fun shouldBeReturnInstrunctionsProdBug() {

        val json = ResourceUtils().openFile("weather_response_200.json")

        val adapter =
            moshi.adapter(WeatherResponse::class.java)

        val poiResponse = adapter.fromJson(json!!)

        poiResponse?.timezone?.isNotEmpty()?.let { assertTrue(it) }
    }

}