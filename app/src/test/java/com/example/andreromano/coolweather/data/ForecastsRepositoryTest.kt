package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.*
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastsRepositoryTest {

    private lateinit var repository: ForecastsRepository

    @Mock
    private lateinit var local: ForecastsLocalDataSource

    @Mock
    private lateinit var remote: ForecastsRemoteDataSource

    private val city = City(
        id = 123123,
        name = "Almada",
        countryIso2 = "PT"
    )

    private val forecast100 = ThreeHourForecast(100, 1, city, 10.0, emptyList(), 100)
    private val forecast200 = ThreeHourForecast(200, 1, city, 10.0, emptyList(), 100)
    private val forecast300 = ThreeHourForecast(300, 1, city, 10.0, emptyList(), 100)

    private val successLocalData = listOf(
        forecast100,
        forecast200,
        forecast300
    )

    @Before
    fun setUp() {
        repository = ForecastsRepository(local, remote)
    }

    @Test
    fun `getting forecasts by city should return the result from local`() {
        val expected = Resource.Success(successLocalData)
        given { local.getForecastsByCity(city, null, null) }.willReturn(expected)
        val actual = repository.getForecastsByCity(city, null, null)

        verify(local).getForecastsByCity(city, null, null)
        actual shouldEqual expected
    }

}