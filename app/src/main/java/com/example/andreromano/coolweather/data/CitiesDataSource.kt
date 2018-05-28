package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Either


interface CitiesDataSource {

    fun getCitiesByName(searchQuery: String): Either<Error, List<City>>

}