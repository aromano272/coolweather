package com.example.andreromano.coolweather.data

import android.support.annotation.VisibleForTesting
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Either

// TODO: Make Singleton
class CitiesRepository(
    private val cache: CitiesCacheDataSource,
    private val local: CitiesLocalDataSource
) {

    fun getCitiesByName(searchQuery: String): Either<Error, List<City>> {
        val cachedSearch = cache.getCitiesByName(searchQuery)
        if (cachedSearch is Either.Success) {
            return cachedSearch
        }

        val result = local.getCitiesByName(searchQuery)

        if (result is Either.Success) {
            cache.saveSearchByName(searchQuery, result.success)
        }

        return result
    }

}