package app.forecastweather.search.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.forecastweather.search.R
import app.forecastweather.search.databinding.ItemForecastBinding
import app.forecastweather.search.model.Forecastday
import app.forecastweather.search.utils.ListUtilAdapter

class ForecastAdapter: RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    var data: List<Forecastday> = emptyList()
        set(newValue) {
            val diffUtil = ListUtilAdapter(field, newValue)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
            field = newValue

            diffUtilResult.dispatchUpdatesTo(this@ForecastAdapter)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastAdapter.ForecastViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val binding = ItemForecastBinding.inflate(inflater, parent, false)
       return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ForecastViewHolder, position: Int) {
        val forecastDay = data[position]
        with(holder.binding){
            Log.d("TEST", forecastDay.day.daily_will_it_rain.toString())
            val weatherIcon = if(forecastDay.day.daily_will_it_rain == 1) "weather_rain_forecast" else "weather_sun_forecast"
            weather.setAnimation("$weatherIcon.json")

            weatherPossibility.progress = forecastDay.day.daily_chance_of_rain
            dayDate.text = forecastDay.date
            highTemp.text = "${forecastDay.day.maxtemp_c} ${holder.itemView.context.getString(R.string.meassure_value)}"
            minTemp.text = "${forecastDay.day.mintemp_c} ${holder.itemView.context.getString(R.string.meassure_value)}"
        }
    }

    override fun getItemCount(): Int = data.size

    class ForecastViewHolder(val binding: ItemForecastBinding): RecyclerView.ViewHolder(binding.root)

}