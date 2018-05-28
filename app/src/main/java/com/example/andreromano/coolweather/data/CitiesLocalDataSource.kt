package com.example.andreromano.coolweather.data

import android.support.annotation.VisibleForTesting
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.CityUtils
import com.example.andreromano.coolweather.Either
import com.example.andreromano.coolweather.FileReader
import com.example.andreromano.coolweather.extensions.withoutAccents
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException


class CitiesLocalDataSource(private val reader: FileReader) : CitiesDataSource {

    @VisibleForTesting
    var cache: List<City>? = null

    // FIXME: Debug
    init {
        cache = listOf(
            City(8013113, "Almada", "PT")
        )
    }

    override fun getCitiesByName(searchQuery: String): Either<Error, List<City>> {
        cache?.let {
            return Either.Success(CityUtils.filterByName(it, searchQuery))
        }

        val response = readCitiesFromJson()
        when (response) {
            is Either.Failure -> return response
            is Either.Success -> {
                val cities = response.success.take(10000)
                cache = cities
                return Either.Success(CityUtils.filterByName(cities, searchQuery))
            }
        }
    }

    private fun readCitiesFromJson(): Either<Error, List<City>> {
        val json = reader.readFile("city.list.min.json")

        if (json == null) {
            return Either.Failure(Error("Failure reading City file"))
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, City::class.java)
        val adapter: JsonAdapter<List<City>> = moshi.adapter(type)

        try {
            val cities = adapter.fromJson(json.toString(Charsets.UTF_8))
            if (cities == null) {
                throw IOException()
            }

            return Either.Success(cities)
        } catch (ex: IOException) {
            return Either.Failure(Error("Failure parsing City json"))
        }
    }

}