package app.forecastweather.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.FrameLayout
import androidx.navigation.findNavController
import app.forecastweather.search.constants.Constants
import app.forecastweather.search.databinding.ActivityMainBinding
import app.forecastweather.search.ui.search.SearchFragment
import app.forecastweather.search.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.location

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.search -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.nav_search_fragment)
                    true
                }
                R.id.location -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.nav_weather_fragment)
                    true
                }

                else -> {
                    false
                }
            }

        }
    }
}