package app.forecastweather.search.data.repository

import app.forecastweather.search.data.RetrofitInstance
import app.forecastweather.search.model.Weather
import retrofit2.Response

class WeatherRepository {

    suspend fun getWeather(city: String): Response<Weather>{
        return RetrofitInstance.api.getForecast(query = city)
    }

}