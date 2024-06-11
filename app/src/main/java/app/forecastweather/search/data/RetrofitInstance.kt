package app.forecastweather.search.data

import app.forecastweather.search.constants.Constants
import app.forecastweather.search.data.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: WeatherApi by lazy{
        retrofit.create(WeatherApi::class.java)
    }

}