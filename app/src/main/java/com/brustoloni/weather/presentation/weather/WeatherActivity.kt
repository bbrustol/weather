package com.brustoloni.weather.presentation.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brustoloni.weather.R
import com.brustoloni.weather.utils.replaceFragment

class WeatherActivity : AppCompatActivity() {

    private val mFragment by lazy { WeatherFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (savedInstanceState == null)
            replaceFragment(R.id.framelayout_container, mFragment,
                WEATHER_FRAGMENT_TAG
            )
    }
}
