package com.brustoloni.weather.utils

import android.text.format.DateFormat
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("android:src")
fun setSrcVector(view: ImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}

fun timestampToString(dates: Long, dateFormat: String = "EEE, MMM dd yyyy - HH:mm", timezone: String): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = dates

    if (cal.get(Calendar.YEAR) == 1970) {
        cal.timeInMillis = dates * 1000
    }

    cal.timeZone = if (timezone.isEmpty()) { TimeZone.getDefault() } else { TimeZone.getTimeZone(timezone) }

    return DateFormat.format(dateFormat, cal).toString()
}
