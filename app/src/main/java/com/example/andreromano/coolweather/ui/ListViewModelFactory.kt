package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherByCity
import com.example.andreromano.coolweather.usecases.SearchCityByName


class ListViewModelFactory(
    private val searchCityByName: SearchCityByName,
    private val getCurrentWeatherByCity: GetCurrentWeatherByCity
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(searchCityByName, getCurrentWeatherByCity) as T
    }
}