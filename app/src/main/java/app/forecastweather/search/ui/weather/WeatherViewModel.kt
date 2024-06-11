package app.forecastweather.search.ui.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.forecastweather.search.data.repository.WeatherRepository
import app.forecastweather.search.model.Weather
import app.forecastweather.search.utils.Utils
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WeatherRepository()
    private val settings = Utils(application)

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather> = _currentWeather

    private val _errorRequest = MutableLiveData<Boolean>()
    val errorRequest: LiveData<Boolean> = _errorRequest

    fun loadCurrentWeather(city: String) = viewModelScope.launch {
            val response = repository.getWeather(city)
            if(response.isSuccessful && response.body() !=  null){
                Log.d("TEST", response.body().toString())
                _currentWeather.value = response.body()
            } else {
                Log.d("ERROR", response.message().toString())
                _errorRequest.value = true
            }
    }

}