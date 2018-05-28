package com.example.andreromano.coolweather

import com.example.andreromano.coolweather.extensions.withoutAccents


object CityUtils {

    fun filterByName(cities: List<City>, searchQuery: String): List<City> =
        cities.filter { city -> city.name.withoutAccents().startsWith(searchQuery.withoutAccents(), ignoreCase = true) }

}