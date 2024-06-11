package app.forecastweather.search.data.api

import app.forecastweather.search.constants.Constants
import app.forecastweather.search.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String = "d83781fca1f64e29bc6211055232901",
        @Query("q") query: String,
        @Query("days") days: Int = 4
    ): Response<Weather>

}