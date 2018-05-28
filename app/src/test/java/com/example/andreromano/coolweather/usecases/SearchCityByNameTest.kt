package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.data.CitiesLocalDataSource
import com.example.andreromano.coolweather.data.CitiesRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchCityByNameTest {

    private lateinit var searchCityByName: SearchCityByName

    @Mock
    private lateinit var citiesRepository: CitiesRepository

    @Before
    fun setUp() {
        searchCityByName = SearchCityByName(citiesRepository)
    }

    @Test
    fun `should get data from the repository`() {
        runBlocking { searchCityByName.run(SearchCityByName.Params("test")) }
        verify(citiesRepository).getCitiesByName(any())
    }

}