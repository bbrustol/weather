package com.brustoloni.weather.business

import android.provider.MediaStore.Video.VideoColumns.CATEGORY
import com.brustoloni.weather.data.entity.weather.response.WeatherResponse
import com.brustoloni.weather.data.infraestructure.*
import com.brustoloni.weather.data.infraestructure.DataMock.Companion.createAlert
import com.brustoloni.weather.data.infraestructure.DataMock.Companion.createCurrently
import com.brustoloni.weather.data.provider.WeatherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


class WeatherBusinessTest {

    private lateinit var business: WeatherBusiness

    @MockK(relaxUnitFun = true)
    private lateinit var remoteProvider: WeatherProvider

    private val weatherResponse = DataMock.createList()

    private val weather = DataMock.createWeather()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        business = WeatherBusiness(remoteProvider)
    }

    @Test
    fun shouldBeRetrieveFromRemote() = runBlocking {

        coEvery { remoteProvider.fetchWeather(any(), any(), any()) } returns Success(
            WeatherResponse(  listOf(createAlert()),
                createCurrently(),
                DataMock.createDaily(),
                null,
                DataMock.createHourly(),
                DataMock.double,
                DataMock.double,
                0,
                "Europe/Stockholm")
        )

        val fetch = business.fetchWeather("", "", "")

        fetch.handle({ assertEquals(weather, data) })
    }


    @Test
    fun shouldBeErrorFromRemote() = runBlocking {

        coEvery { remoteProvider.fetchWeather(any(), any(), any()) } returns Failure(Error(CATEGORY))

        val fetch = business.fetchWeather("", "", "")

        fetch.handle({ fail() }, { assertEquals(CATEGORY, data.message) })
    }

    @Test
    fun shouldBeRetrieveWeather() = runBlocking {

        coEvery { remoteProvider.fetchWeather(any(), any(), any()) } returns Success(
            WeatherResponse(  listOf(createAlert()),
                createCurrently(),
                DataMock.createDaily(),
                null,
                DataMock.createHourly(),
                DataMock.double,
                DataMock.double,
                0,
                "Europe/Stockholm")
        )

        val fetch = business.fetchWeather("", "", "")

        fetch.handle({
            assertEquals(weather, data)
            assertEquals(weatherResponse[0].hourly, data.hourly)
            assertEquals(weatherResponse[0].alerts, data.alerts)
            assertEquals(weatherResponse[0].currently, data.currently)
            assertEquals(weatherResponse[0].daily, data.daily)
            assertEquals(weatherResponse[0].flags, data.flags)
            assertEquals(weatherResponse[0].latitude, data.latitude)
            assertEquals(weatherResponse[0].longitude, data.longitude)
            assertEquals(weatherResponse[0].offset, data.offset)
            assertEquals(weatherResponse[0].timezone, data.timezone)


        })
    }

    @Test
    fun shouldBeRetrieveErrorWeather() = runBlocking {

        coEvery { remoteProvider.fetchWeather(any(), any(), any()) } returns Failure(
            Error(
                DataMock.ILLEGAL_ARGUMENT
            )
        )

        val fetch = business.fetchWeather("", "", "")

        fetch.handle({ fail() }, { assertEquals(DataMock.ILLEGAL_ARGUMENT, data.message) })
    }

    @Test
    fun shouldBeRetrieveErrorWhenFetchWeather() = runBlocking {

        coEvery { remoteProvider.fetchWeather(any(), any(), any()) } returns Failure(
            Error(
                DataMock.ILLEGAL_ARGUMENT
            )
        )

        val fetch = business.fetchWeather("", "", "")

        fetch.handle({ fail() }, { assertEquals(DataNotAvailable().javaClass, this.networkState.javaClass) })
    }
}