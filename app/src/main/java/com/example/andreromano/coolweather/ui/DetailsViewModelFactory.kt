package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.data.TemperatureConverter
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherAndNext4DaysDailyForecasts


class DetailsViewModelFactory(
    private val city: City,
    private val getCurrentWeatherAndNext4DaysDailyForecasts: GetCurrentWeatherAndNext4DaysDailyForecasts,
    private val temperatureConverter: TemperatureConverter
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            city,
            getCurrentWeatherAndNext4DaysDailyForecasts,
            temperatureConverter
        ) as T
    }
}