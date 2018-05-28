package com.example.andreromano.coolweather.data

import android.support.annotation.VisibleForTesting
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.CityUtils
import com.example.andreromano.coolweather.Either


class CitiesCacheDataSource : CitiesDataSource {

    @VisibleForTesting
    val searchCityByNameCache: MutableMap<String, List<City>> = mutableMapOf()

    override fun getCitiesByName(searchQuery: String): Either<Error, List<City>> {
        var query: String = searchQuery
        while (query.isNotEmpty()) {
            searchCityByNameCache[query]?.let {
                return if (searchQuery == query) {
                    Either.Success(it)
                } else {
                    val result = CityUtils.filterByName(it, searchQuery)
                    searchCityByNameCache[searchQuery] = result
                    Either.Success(result)
                }
            }
            query = query.dropLast(1)
        }
        return Either.Failure(Error("Search not cached"))
    }

    fun saveSearchByName(searchQuery: String, cities: List<City>) {
        searchCityByNameCache[searchQuery] = cities
    }

}