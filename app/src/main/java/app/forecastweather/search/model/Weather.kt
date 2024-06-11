package app.forecastweather.search.model

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)