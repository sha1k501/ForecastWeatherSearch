package app.forecastweather.search.ui.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.forecastweather.search.R
import app.forecastweather.search.databinding.FragmentWeatherBinding
import app.forecastweather.search.ui.adapter.ForecastAdapter
import app.forecastweather.search.ui.search.SearchFragment
import app.forecastweather.search.utils.Utils
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.location.LocationServices


class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding get() = _binding!!
    private var adapter = ForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val utils = Utils(requireContext())

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Permission's not granted", Toast.LENGTH_LONG).show()
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            viewModel.currentWeather.observe(viewLifecycleOwner) { weather ->
                load.visibility = View.GONE
                if(utils.currentCity != null) {
                    utils.lastCity = weather.location.name
                }
                utils.currentCity = null
                adapter.data = weather.forecast.forecastday
                cityName.text = "${weather.location.name}, ${weather.location.country}"
                weatherTitle.text = weather.current.condition.text
                val background = if(weather.forecast.forecastday.first().day.daily_will_it_rain == 1) "rain_background" else "sun_background"
                binding.weather.setAnimation("$background.json")
                binding.weather.repeatCount = LottieDrawable.INFINITE
                binding.weather.playAnimation()

                avgTemp.text = "${weather.current.temp_c}${getString(R.string.meassure_value)}"
                highTemp.text = "${getString(R.string.max_title)} ${weather.forecast.forecastday.first().day.maxtemp_c}${getString(R.string.meassure_value)}"
                minTemp.text = "${getString(R.string.min_title)} ${weather.forecast.forecastday.first().day.mintemp_c}${getString(R.string.meassure_value)}"
            }

        }
        return binding.root
    }

    private fun getLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Handle location
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val utils = Utils(requireContext())

                    if(utils.currentCity == null) {
                        viewModel.loadCurrentWeather("$latitude,$longitude")
                    }

                }
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.nav_search_fragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onStart() {
        super.onStart()
        val utils = Utils(requireContext())
        if(utils.currentCity != null){
            utils.currentCity?.let {
                viewModel.loadCurrentWeather(it)
            }
        }

    }

}