package com.brustoloni.weather.presentation.weather


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brustoloni.weather.R
import com.brustoloni.weather.data.entity.weather.FormattedWeatherHourly
import com.brustoloni.weather.data.entity.weather.response.FormattedWeatherResponse

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var myContext: Context
    private val viewPool = RecyclerView.RecycledViewPool()
    private var updatesList: List<FormattedWeatherResponse> = listOf()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_DETAIL = 1
        private const val TYPE_HOURLY = 2
        private const val TYPE_DAILY = 3
    }

    //region -> override methods
    override fun getItemCount(): Int = updatesList.size

    override fun getItemViewType(position: Int): Int {
        return when (updatesList[position].type) {
            ViewHolderTypeEmum.HEADER.ordinal -> TYPE_HEADER
            ViewHolderTypeEmum.DETAIL.ordinal -> TYPE_DETAIL
            ViewHolderTypeEmum.HOURLY.ordinal -> TYPE_HOURLY
            else -> TYPE_DAILY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        myContext = parent.context

        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> {
                HeaderViewHolder(inflate(
                    layoutInflater,
                    R.layout.item_card_weather_header, parent, false
                ))
            }

            TYPE_DETAIL -> {
                DetailViewHolder(inflate(
                    layoutInflater,
                    R.layout.item_card_weather_detail, parent, false
                ))
            }

            TYPE_HOURLY -> {
                HourlyViewHolder(inflate(
                    layoutInflater,
                    R.layout.item_card_weather_recyclerview_pool, parent, false
                ))
            }

            TYPE_DAILY -> {
                DailyViewHolder(inflate(
                    layoutInflater,
                    R.layout.item_card_weather_daily, parent, false
                ))

            } else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val element = updatesList[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(element)
            is DetailViewHolder -> holder.bind(element)
            is HourlyViewHolder -> holder.bind(element.hourly)
            is DailyViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    fun update(update: List<FormattedWeatherResponse>) {
        updatesList = update
        notifyDataSetChanged()
    }

    //region -> inner classes
    inner class HeaderViewHolder(private val binding: com.brustoloni.weather.databinding.ItemCardWeatherHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: FormattedWeatherResponse) = with(binding) {
            item = response
            executePendingBindings()
        }
    }

    inner class DetailViewHolder(private val binding: com.brustoloni.weather.databinding.ItemCardWeatherDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: FormattedWeatherResponse) = with(binding) {
            item = response
            executePendingBindings()
        }
    }

    inner class HourlyViewHolder(private val binding: com.brustoloni.weather.databinding.ItemCardWeatherRecyclerviewPoolBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: ArrayList<FormattedWeatherHourly>) {
            binding.rvWeatherHourly.adapter = WeatherHourlyAdapter(response)
            binding.rvWeatherHourly.layoutManager = LinearLayoutManager(binding.rvWeatherHourly.context, LinearLayout.HORIZONTAL, false)
            binding.rvWeatherHourly.setRecycledViewPool(viewPool)
        }
    }

    inner class DailyViewHolder(private val binding: com.brustoloni.weather.databinding.ItemCardWeatherDailyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: FormattedWeatherResponse) = with(binding) {
            item = response
            executePendingBindings()
        }
    }
    //endregion
}