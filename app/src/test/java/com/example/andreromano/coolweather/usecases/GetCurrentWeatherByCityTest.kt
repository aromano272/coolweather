package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ThreeHourForecast
import com.example.andreromano.coolweather.data.ForecastsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.joda.time.DateTimeUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCurrentWeatherByCityTest {

    private lateinit var getCurrentWeatherByCity: GetCurrentWeatherByCity

    @Mock
    private lateinit var forecastsRepository: ForecastsRepository

    private val city = City(
        id = 123123,
        name = "Almada",
        countryIso2 = "PT"
    )

    private val forecast100 = ThreeHourForecast(100, 1, city, 10.0, emptyList(), 100)
    private val forecast200 = ThreeHourForecast(200, 1, city, 10.0, emptyList(), 100)
    private val forecast300 = ThreeHourForecast(300, 1, city, 10.0, emptyList(), 100)

    private val successRepositoryData = listOf(
        forecast100,
        forecast200,
        forecast300
    )

    @Before
    fun setUp() {
        getCurrentWeatherByCity = GetCurrentWeatherByCity(forecastsRepository)
        given { forecastsRepository.getForecastsByCity(city) }.willReturn(Resource.Success(successRepositoryData))
    }

    @Test
    fun `if the repository succeeds it should filter the results and return an error if there arent any past forecasts`() {
        DateTimeUtils.setCurrentMillisFixed(50)
        val expected = Resource.Failure::class
        given { forecastsRepository.getForecastsByCity(city) }.willReturn(Resource.Success(successRepositoryData))
        val actual = runBlocking { getCurrentWeatherByCity.run(GetCurrentWeatherByCity.Params(city)) }

        actual shouldBeInstanceOf expected
    }

    @Test
    fun `if the repository succeeds it should filter the results and pick the one with the most recent forecast data but only in the past not the future`() {
        DateTimeUtils.setCurrentMillisFixed(250)
        val expected = Resource.Success(forecast200)
        given { forecastsRepository.getForecastsByCity(city) }.willReturn(Resource.Success(listOf(forecast300, forecast200, forecast100)))
        val actual = runBlocking { getCurrentWeatherByCity.run(GetCurrentWeatherByCity.Params(city)) }

        actual shouldEqual expected
    }

    @Test
    fun `if the repository fails it should return that error`() {
        val failure = Resource.Failure(null, Error("Some Error"))
        given { forecastsRepository.getForecastsByCity(city) }.willReturn(failure)
        val result = runBlocking { getCurrentWeatherByCity.run(GetCurrentWeatherByCity.Params(city)) }

        result shouldEqual failure
    }

    @Test
    fun `should get data from the repository`() {
        runBlocking { getCurrentWeatherByCity.run(GetCurrentWeatherByCity.Params(city)) }

        verify(forecastsRepository).getForecastsByCity(city)
    }

}