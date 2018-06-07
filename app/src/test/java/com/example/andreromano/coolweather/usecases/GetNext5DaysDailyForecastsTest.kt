package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ThreeHourForecast
import com.example.andreromano.coolweather.WeatherCondition
import com.example.andreromano.coolweather.data.ForecastsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNext5DaysDailyForecastsTest {

    private lateinit var getNext5DaysDailyForecasts: GetNext5DaysDailyForecasts

    @Mock
    private lateinit var forecastsRepository: ForecastsRepository

    private val city = City(
        id = 123123,
        name = "Almada",
        countryIso2 = "PT"
    )

    private val sunny = WeatherCondition(1, "1d")

    private val forecast100 = ThreeHourForecast(100, 1, city, 10.0, listOf(sunny), 100)
    private val forecast200 = ThreeHourForecast(200, 1, city, 10.0, emptyList(), 100)
    private val forecast300 = ThreeHourForecast(300, 1, city, 10.0, emptyList(), 100)

    private val successRepositoryData = listOf(
        forecast100,
        forecast200,
        forecast300
    )

    @Before
    fun setUp() {
        getNext5DaysDailyForecasts = GetNext5DaysDailyForecasts(forecastsRepository)
        given { forecastsRepository.getForecastsByCity(eq(city), any(), any()) }.willReturn(Resource.Success(successRepositoryData))
    }

    @Test
    fun `should fetch data from the repository only for the next 5 days`() {
        DateTimeUtils.setCurrentMillisFixed(0)
        val now = DateTime.now()
        val firstDayMin = now.plusDays(1).withTimeAtStartOfDay().millis
        val lastDayMax = now.plusDays(5).millisOfDay().withMaximumValue().millis

        runBlocking { getNext5DaysDailyForecasts.run(GetNext5DaysDailyForecasts.Params(city)) }

        verify(forecastsRepository).getForecastsByCity(city, firstDayMin, lastDayMax)
    }

}