package com.example.andreromano.coolweather.ui


import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andreromano.coolweather.AssetFileReader
import com.example.andreromano.coolweather.City

import com.example.andreromano.coolweather.data.CitiesCacheDataSource
import com.example.andreromano.coolweather.data.CitiesLocalDataSource
import com.example.andreromano.coolweather.data.CitiesRepository
import com.example.andreromano.coolweather.databinding.FragmentListBinding
import com.example.andreromano.coolweather.extensions.reObserve
import com.example.andreromano.coolweather.usecases.SearchCityByName
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var cityAdapter: CityAdapter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Implement DI
        val repository = CitiesRepository(
            CitiesCacheDataSource(),
            CitiesLocalDataSource(AssetFileReader(context!!.applicationContext))
        )
        val searchCityByName = SearchCityByName(repository)
        viewModel = ViewModelProviders.of(this, ListViewModelFactory(searchCityByName)).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentListBinding.inflate(inflater)
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
