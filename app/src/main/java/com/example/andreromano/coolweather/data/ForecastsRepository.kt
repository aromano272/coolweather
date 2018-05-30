package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ThreeHourForecast

// TODO: Make Singleton
class ForecastsRepository(
    private val local: CitiesLocalDataSource
) : ForecastsDataSource {

    override fun getForecastsByCity(city: City): Resource<List<ThreeHourForecast>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}