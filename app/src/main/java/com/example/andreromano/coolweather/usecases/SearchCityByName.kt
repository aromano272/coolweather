package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.CitiesRepository


class SearchCityByName(private val citiesRepository: CitiesRepository) : CoolUseCase<List<City>, SearchCityByName.Params>() {

    data class Params(val searchQuery: String)

    override fun run(params: Params): Either<Error, List<City>> {
        return citiesRepository.getCitiesByName(params.searchQuery)
    }

}