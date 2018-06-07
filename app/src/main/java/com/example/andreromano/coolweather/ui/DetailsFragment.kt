package com.example.andreromano.coolweather.ui

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ThreeHourForecast
import com.example.andreromano.coolweather.data.*
import com.example.andreromano.coolweather.databinding.FragmentDetailsBinding
import com.example.andreromano.coolweather.network.OpenWeatherService
import com.example.andreromano.coolweather.network.ServiceGenerator
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherByCity
import com.example.andreromano.coolweather.usecases.GetNext5DaysDailyForecasts


class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailsViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var city: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        city = arguments!!["CITY"]!! as City

        // TODO: Implement DI
        val database = Room.databaseBuilder(activity!!.applicationContext, AppDatabase::class.java, "dbname").build()
        val forecastsRepository = ForecastsRepository(
            ForecastsLocalDataSource(database.threeHourForecastDao()),
            ForecastsRemoteDataSource(OpenWeatherService(ServiceGenerator.retrofit))
        )
        val getCurrentWeatherByCity = GetCurrentWeatherByCity(forecastsRepository)
        val getNext5DaysDailyForecasts = GetNext5DaysDailyForecasts(forecastsRepository)
        val temperatureConverter = CelsiusTemperatureConverter()
        val viewModelFactory = DetailsViewModelFactory(
            city,
            getCurrentWeatherByCity,
            getNext5DaysDailyForecasts,
            temperatureConverter
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.loadingSearchResults.reObserve(this, onLoadingSearchResults)
//        viewModel.citiesSearchResults.reObserve(this, onCitiesSearchResults)
//        viewModel.currentWeatherByCity.reObserve(this, onCurrentWeatherByCity)
        viewModel.start(city)
    }

    val onCurrentWeatherByCity = Observer<Resource<ThreeHourForecast>> { resource ->
        val message = when (resource) {
            is Resource.Loading -> "LOADING -> data: ${resource.data}"
            is Resource.Success -> "SUCCESS -> data: ${resource.data}"
            is Resource.Failure -> "FAILURE -> data: ${resource.data} error: ${resource.error}"
            null                -> {
                val message = "onCurrentWeatherByCity: NULL VALUE"
                Log.e("DEBUG", message)
                message
            }
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).show()
    }

}
