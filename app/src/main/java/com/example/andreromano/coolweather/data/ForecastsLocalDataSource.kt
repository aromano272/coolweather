package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.*


class ForecastsLocalDataSource(
    private val dao: ThreeHourForecastDao
) : ForecastsDataSource {

    override fun getForecastsByCity(city: City, from: Long?, to: Long?): Resource<List<ThreeHourForecast>> {
        if (from != null && to != null) return Resource.Success(dao.getByCity(city.id, from, to))
        return Resource.Success(dao.getByCity(city.id))
    }

}