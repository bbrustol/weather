package com.brustoloni.weather.presentation.weather

import android.app.Application
import android.view.View.GONE
import androidx.lifecycle.MutableLiveData
import com.brustoloni.weather.business.WeatherBusiness
import com.brustoloni.weather.data.entity.weather.FormattedWeatherHourly
import com.brustoloni.weather.data.entity.weather.response.FormattedWeatherResponse
import com.brustoloni.weather.data.entity.weather.response.WeatherResponse
import com.brustoloni.weather.data.infraestructure.Failure
import com.brustoloni.weather.data.infraestructure.Success
import com.brustoloni.weather.data.infraestructure.handle
import com.brustoloni.weather.presentation.BaseViewModel
import com.brustoloni.weather.utils.Constants.Companion.CELSIUS
import com.brustoloni.weather.utils.timestampToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherBusiness: WeatherBusiness,
    private val application: Application,
    private val coroutineScope: CoroutineScope
) : BaseViewModel(application) {

    private var mLatLon:String = ""

    val dataReceived: MutableLiveData<List<FormattedWeatherResponse>> = MutableLiveData()
    val listVisibility = MutableLiveData<Int>().apply { value = GONE }
    val flagFirstLoad = MutableLiveData<Boolean>().apply { value = false }

    fun start(latLon: String) {
        mLatLon = latLon

        configVisibility(ViewState.LOADING)
        coroutineScope.launch {
            //us - miles, Fahrenheit
            //si - kilometer, Celsius
            //auto - automatically select units based on geographic location
            val resource = weatherBusiness.fetchWeather(latLon, "si", "flags")
            resource.handle(success(), failure())
        }
    }

    //region -> private methods
    private fun failure(): Failure.() -> Unit = {
        configVisibility(ViewState.ERROR)
    }

    private fun success(): Success<WeatherResponse>.() -> Unit = {

        dataReceived.value = formatResponse(this.data)
        configVisibility(ViewState.SUCCESS)
    }

    private fun formatResponse(response: WeatherResponse): ArrayList<FormattedWeatherResponse> {

        val formattedWeatherResponse: ArrayList<FormattedWeatherResponse> = arrayListOf()

        //region -> HEADER
        formattedWeatherResponse.add(
            FormattedWeatherResponse(
            icon = application.applicationContext.resources.getIdentifier(response.currently?.icon?.replace("-","_"),"drawable", application.packageName),
            temperature = response.currently?.temperature?.toInt().toString() + CELSIUS,
            summary = response.currently?.summary ?: "",
            timezone = response.timezone ?: "",
            time = timestampToString(response.currently?.time ?: 0, timezone = response.timezone ?: ""),
            type = ViewHolderTypeEmum.HEADER.ordinal
        )
        )
        //endregion
        //region -> DETAIL ITEMS
        formattedWeatherResponse.add(
            FormattedWeatherResponse(
                icon = application.baseContext.resources.getIdentifier("thermometer","drawable", application.packageName),
            key = "Feels Like",
            value = response.currently?.apparentTemperature?.toInt().toString() + CELSIUS,
            type = ViewHolderTypeEmum.DETAIL.ordinal
        )
        )

        formattedWeatherResponse.add(
            FormattedWeatherResponse(
            icon = application.baseContext.resources.getIdentifier("humidity","drawable", application.packageName),
            key = "Humidity",
            value = response.currently?.humidity?.times(100)?.toInt().toString()+"%",
            type = ViewHolderTypeEmum.DETAIL.ordinal
        )
        )


        val precipitation = response.currently?.precipIntensity
        formattedWeatherResponse.add(
            FormattedWeatherResponse(
            icon = application.baseContext.resources.getIdentifier("drops","drawable", application.packageName),
            key = "Precipitation",
            value = "$precipitation cm",
            type = ViewHolderTypeEmum.DETAIL.ordinal
        )
        )

        val pressure = response.currently?.pressure
        formattedWeatherResponse.add(
            FormattedWeatherResponse(
            icon = application.baseContext.resources.getIdentifier("pressure","drawable", application.packageName),
            key = "Pressure",
            value = "$pressure hPa",
            type = ViewHolderTypeEmum.DETAIL.ordinal
        )
        )

        formattedWeatherResponse.add(
            FormattedWeatherResponse(
            icon = application.baseContext.resources.getIdentifier("uv_index","drawable", application.packageName),
            key = "UV Index",
            value = response.currently?.uvIndex.toString(),
            type = ViewHolderTypeEmum.DETAIL.ordinal
        )
        )

        val visibility = response.currently?.visibility?.toInt()
        formattedWeatherResponse.add(
            FormattedWeatherResponse(
                icon = application.baseContext.resources.getIdentifier("eye","drawable", application.packageName),
            key = "Visibility",
            value = "$visibility km",
            type = ViewHolderTypeEmum.DETAIL.ordinal
        )
        )
        //endregion
        //region -> HOURLY
        val hourly: ArrayList<FormattedWeatherHourly> = arrayListOf()
        for (hour in response.hourly?.data!!) {
            if (hour != null) {
                hourly.add(FormattedWeatherHourly(
                        application.applicationContext.resources.getIdentifier(hour.icon?.replace("-","_"),"drawable", application.packageName),
                        timestampToString(hour.time?.toLong() ?: 0,"HH", response.timezone ?: ""),
                        hour.temperature?.toInt().toString() + CELSIUS
                    )
                )
            }
        }

        formattedWeatherResponse.add(
            FormattedWeatherResponse(
            icon = 0,
            hourly = hourly,
            type = ViewHolderTypeEmum.HOURLY.ordinal
        )
        )
        //endregion
        //region -> DAILY
        for (day in response.daily?.data!!) {
            if (day != null) {
                formattedWeatherResponse.add(
                    FormattedWeatherResponse(
                        weekOfDay = timestampToString(day.time?.toLong() ?: 0,"EEEE", response.timezone ?: ""),
                        icon = application.applicationContext.resources.getIdentifier(day.icon?.replace("-","_"),"drawable", application.packageName),
                        temperatureMin = day.temperatureMin?.toInt().toString() + CELSIUS,
                        temperatureMax = day.temperatureMax?.toInt().toString() + CELSIUS,
                        type = ViewHolderTypeEmum.DAILY.ordinal
                    )
                )
            }
        }
        return formattedWeatherResponse
    }
    //endregion

    override fun configVisibility(viewState: ViewState) {
        super.configVisibility(viewState)
        val result = setupViewState(viewState)
        listVisibility.value = result.showData
    }

    override fun tryAgain() {
        start(mLatLon)
    }

}