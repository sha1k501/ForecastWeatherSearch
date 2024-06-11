package app.forecastweather.search.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.forecastweather.search.data.repository.WeatherRepository
import app.forecastweather.search.model.Weather
import app.forecastweather.search.utils.Utils
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    val repository = WeatherRepository()
    val settings = Utils(application)

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather> = _currentWeather

    private val _errorRequest = MutableLiveData<Boolean>()
    val errorRequest: LiveData<Boolean> = _errorRequest

    fun loadLastWeather() = viewModelScope.launch {
        settings.lastCity?.let {
            val response = repository.getWeather(it)
            if(response.isSuccessful && response.body() !=  null){
                Log.d("TEST", response.body().toString())
                _currentWeather.value = response.body()
            } else {
                _errorRequest.value = true
            }
        }

    }
}