package app.forecastweather.search.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.forecastweather.search.R
import app.forecastweather.search.databinding.FragmentSearchBinding
import app.forecastweather.search.ui.adapter.ForecastAdapter
import app.forecastweather.search.ui.weather.WeatherFragment
import app.forecastweather.search.utils.Utils

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private var adapter = ForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val utils = Utils(requireContext())
        binding.textInputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.white)

        if(utils.lastCity != null){
            binding.textView.text = "${getString(R.string.forecast_title)} for ${utils.lastCity}"
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewModel.currentWeather.observe(viewLifecycleOwner){
                adapter.data = it.forecast.forecastday
            }
        } else {
            binding.textView3.visibility = View.VISIBLE
        }

        binding.button.setOnClickListener {
            if(binding.editText.text.toString().isNotEmpty()){
                utils.currentCity = binding.editText.text.toString()
                findNavController().navigate(R.id.nav_weather_fragment)
            }
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadLastWeather()
    }

}