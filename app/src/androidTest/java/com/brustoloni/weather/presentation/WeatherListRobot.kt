package com.brustoloni.weather.presentation

import androidx.recyclerview.widget.RecyclerView
import com.brustoloni.weather.R
import com.brustoloni.weather.presentation.setup.BaseRobot

class WeatherListRobot : BaseRobot() {

    fun withErrorImage(): WeatherListRobot {
        matchDrawable(R.id.imageStatus, R.drawable.sad_cloud)
        return this
    }

    fun withErrorText(): WeatherListRobot {
        matchText(R.string.error_msg_try_again)
        return this
    }

    fun clickInTryAgain(): WeatherListRobot {
        click(R.id.tryAgainButton)
        return this
    }

    fun waitAlternativeView(): WeatherListRobot {
        Thread.sleep(1000)
        matchId(R.id.imageStatus)
        return this
    }

    fun scrollToLastPosition(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?): WeatherListRobot {
        scrollRecyclerView(R.id.rv_weather, adapter)
        return this
    }
}