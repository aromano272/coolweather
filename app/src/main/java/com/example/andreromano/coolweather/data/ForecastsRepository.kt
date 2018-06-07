package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ThreeHourForecast

// TODO: Make Singleton
class ForecastsRepository(
    private val local: ForecastsLocalDataSource,
    private val remote: ForecastsRemoteDataSource
) : ForecastsDataSource {

    override fun getForecastsByCity(city: City, from: Long?, to: Long?): Resource<List<ThreeHourForecast>> {
        val localResult = local.getForecastsByCity(city, from, to)
        val shouldFetch = true
        return if (shouldFetch) {
            remote.getForecastsByCity(city, from, to)
        } else {
            localResult
        }
    }
}