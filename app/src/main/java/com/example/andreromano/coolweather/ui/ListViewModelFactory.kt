package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.andreromano.coolweather.usecases.SearchCityByName


class ListViewModelFactory(
    private val searchCityByName: SearchCityByName
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(searchCityByName) as T
    }
}