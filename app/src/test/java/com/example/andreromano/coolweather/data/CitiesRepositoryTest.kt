package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Coordinates
import com.example.andreromano.coolweather.Either
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitiesRepositoryTest {

    private lateinit var repository: CitiesRepository

    @Mock
    private lateinit var cache: CitiesCacheDataSource

    @Mock
    private lateinit var local: CitiesLocalDataSource

    @Before
    fun setUp() {
        repository = CitiesRepository(cache, local)
    }

    @Test
    fun `should get data from the local service if cache isnt available`() {
        given { cache.getCitiesByName(any()) }.willReturn(Either.Failure(Error()))
        given { local.getCitiesByName(any()) }.willReturn(Either.Success(listOf()))

        repository.getCitiesByName("TEST")
        verify(cache).getCitiesByName("TEST")
        verify(local).getCitiesByName("TEST")
    }

    @Test
    fun `should return cache if its available instead of going to the local service`() {
        val cities = listOf(City(1, "City name", "Country name"))
        val searchQuery = "TEST"
        given { cache.getCitiesByName(searchQuery) }.willReturn(Either.Success(cities))

        val result = repository.getCitiesByName(searchQuery)

        verify(cache).getCitiesByName(searchQuery)

        result shouldEqual Either.Success(cities)
        verify(local, never()).getCitiesByName(any())
    }

    @Test
    fun `when it fetches from local it should save it as cache`() {
        given { cache.getCitiesByName(any()) }.willReturn(Either.Failure(Error()))
        val cities = listOf(City(1, "City name", "Country name"))
        val searchQuery = "TEST"

        given { local.getCitiesByName(any()) }.willReturn(Either.Success(cities))

        repository.getCitiesByName(searchQuery)
        verify(local).getCitiesByName(searchQuery)
        verify(cache).saveSearchByName(searchQuery, cities)
    }

}