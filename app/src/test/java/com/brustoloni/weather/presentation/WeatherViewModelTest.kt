package com.brustoloni.weather.presentation

import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brustoloni.weather.business.WeatherBusiness
import com.brustoloni.weather.data.entity.weather.response.FormattedWeatherResponse
import com.brustoloni.weather.data.entity.weather.response.WeatherResponse
import com.brustoloni.weather.data.infraestructure.*
import com.brustoloni.weather.presentation.weather.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class WeatherViewModelTest {

    @MockK(relaxUnitFun = true)
    private lateinit var business: WeatherBusiness

    @MockK(relaxUnitFun = true)
    private lateinit var observerSuccess: Observer<List<FormattedWeatherResponse>>

    @MockK(relaxUnitFun = true)
    private lateinit var observerError: Observer<Int>

    @MockK(relaxUnitFun = true)
    private lateinit var application: WeatherApplication

    private val formattedResponse = DataMock.createFormattedList()

    private lateinit var viewModel : WeatherViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = WeatherViewModel(business, application, UI)
        every { application.getString(any()) } returns ""
        //icons
        every { application.packageName } returns ""
        every { application.baseContext.getString(any()) } returns " "
        every { application.baseContext.resources.getString(any()) } returns " "
        every { application.applicationContext.resources.getIdentifier(any(), any(), any()) } returns 0
    }

    @Test
    fun shouldBeRetriveFirtAccessWithSuccess() {

        viewModel.dataReceived.value = formattedResponse

        viewModel.dataReceived.observeForever(observerSuccess)


        coEvery { business.fetchWeather(any(), any(), any()) } returns Success(WeatherResponse(listOf(DataMock.createAlert()),
            DataMock.createCurrently(),
            DataMock.createDaily(),
            null,
            DataMock.createHourly(),
            DataMock.double,
            DataMock.double,
            0,
            ""))

        viewModel.start("")

        verify(exactly = 1) { observerSuccess.onChanged(formattedResponse) }
    }

    @Test
    fun shouldBeRetriveFirstAccessWithError() {
        viewModel.alternativePageVisibility.observeForever(observerError)

        coEvery { business.fetchWeather(any(), any(), any()) } returns Failure(Error(), DataNotAvailable())

        viewModel.start("")

        viewModel.tryAgain()

        verify(exactly = 2) { observerError.onChanged(VISIBLE) }
    }
}

