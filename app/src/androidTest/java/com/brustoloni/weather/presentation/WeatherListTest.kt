package com.brustoloni.weather.presentation

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.brustoloni.weather.presentation.setup.BaseInstrumentedTest
import com.brustoloni.weather.presentation.setup.Constants.Companion.EMPTY_WEATHER_LIST
import com.brustoloni.weather.presentation.setup.Constants.Companion.NOT_FOUND_ERROR_CODE
import com.brustoloni.weather.presentation.setup.Constants.Companion.SUCCESS_WEATHER_LIST
import com.brustoloni.weather.presentation.setup.verify
import com.brustoloni.weather.presentation.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_weather.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeatherListTest : BaseInstrumentedTest() {

    @get:Rule
    var activityRule: ActivityTestRule<WeatherActivity> =
        ActivityTestRule(WeatherActivity::class.java, false, false)

    @Rule @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    private val weatherListRobot = WeatherListRobot()

    @Test
    fun shouldShowErrorView() {

        setupMockWebServer(EMPTY_WEATHER_LIST, statusCode = NOT_FOUND_ERROR_CODE)

        activityRule.launchActivity(Intent())

        verify {
            weatherListRobot
                .waitAlternativeView()
                .withErrorImage()
                .withErrorText()
                .clickInTryAgain()
        }
    }

    @Test
    fun scrollRecycler() {

        setupMockWebServer(SUCCESS_WEATHER_LIST)

        activityRule.launchActivity(Intent())

        verify {
            weatherListRobot
                .scrollToLastPosition(activityRule.activity.rv_weather.adapter)
         }
    }
}