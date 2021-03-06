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
import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.*
import com.example.andreromano.coolweather.databinding.FragmentListBinding
import com.example.andreromano.coolweather.extensions.reObserve
import com.example.andreromano.coolweather.network.OpenWeatherService
import com.example.andreromano.coolweather.network.ServiceGenerator
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherByCity
import com.example.andreromano.coolweather.usecases.SearchCityByName

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var cityAdapter: CityAdapter
    private lateinit var progressDialog: ProgressDialog
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Implement DI
        val database = Room.databaseBuilder(activity!!.applicationContext, AppDatabase::class.java, "dbname").build()
        val citiesRepository = CitiesRepository(
            CitiesCacheDataSource(),
            CitiesLocalDataSource(AssetFileReader(context!!.applicationContext))
        )
        val forecastsRepository = ForecastsRepository(
            ForecastsLocalDataSource(database.threeHourForecastDao()),
            ForecastsRemoteDataSource(OpenWeatherService(ServiceGenerator.retrofit))
        )
        val searchCityByName = SearchCityByName(citiesRepository)
        val getCurrentWeatherByCity = GetCurrentWeatherByCity(forecastsRepository)
        viewModel = ViewModelProviders.of(this, ListViewModelFactory(searchCityByName, getCurrentWeatherByCity)).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        cityAdapter = CityAdapter(emptyList(), viewModel::onCityClicked)

        binding.rvCities.apply {
            setHasFixedSize(true)
            adapter = cityAdapter
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadingSearchResults.reObserve(this, onLoadingSearchResults)
        viewModel.citiesSearchResults.reObserve(this, onCitiesSearchResults)
        viewModel.currentWeatherByCity.reObserve(this, onCurrentWeatherByCity)
        viewModel.navigateToDetails.reObserve(this, onNavigateToDetails)
    }

    val onNavigateToDetails = Observer<Event<City>> { event ->
        event?.getContentIfNotHandled()?.let {
            (activity as MainActivity).navigateTo(
                MainActivity.Navigation.DETAILS,
                Bundle().apply { putParcelable("CITY", it) }
            )
        }
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

    val onLoadingSearchResults = Observer<Boolean> { show ->
        if (show != null && show) progressDialog.show()
        else progressDialog.hide()
    }

    val onCitiesSearchResults = Observer<List<City>> { cities ->
        cities?.let {
            cityAdapter.replaceData(it)
        }
    }

}
