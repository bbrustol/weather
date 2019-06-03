package com.brustoloni.weather.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brustoloni.weather.R
import com.brustoloni.weather.data.entity.weather.FormattedWeatherHourly
import com.brustoloni.weather.databinding.ItemCardHorizontalRecyclerWeatherHourlyBinding

class WeatherHourlyAdapter(private val hourly : ArrayList<FormattedWeatherHourly>)
    : RecyclerView.Adapter<WeatherHourlyAdapter.ViewHolder>(){

    override fun getItemCount(): Int = hourly.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = DataBindingUtil.inflate<ItemCardHorizontalRecyclerWeatherHourlyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_card_horizontal_recycler_weather_hourly, parent, false
        )

      return ViewHolder(itemBinding)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(hourly[position])

    inner class ViewHolder(private val binding: ItemCardHorizontalRecyclerWeatherHourlyBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(response: FormattedWeatherHourly) = with(binding) {
            item = response
            executePendingBindings()
        }
    }
}