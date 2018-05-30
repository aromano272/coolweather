package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ThreeHourForecast


interface ForecastsDataSource {

    fun getForecastsByCity(city: City): Resource<List<ThreeHourForecast>>

}