package com.example.andreromano.coolweather.ui

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.data.TemperatureConverter
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherAndNext4DaysDailyForecasts
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    @Mock
    private lateinit var getCurrentWeatherAndNext4DaysDailyForecasts: GetCurrentWeatherAndNext4DaysDailyForecasts

    @Mock
    private lateinit var temperatureConverter: TemperatureConverter

    private val city = City(8013113, "Almada", "PT")

    @Before
    fun setup() {
        viewModel = DetailsViewModel(
            city,
            getCurrentWeatherAndNext4DaysDailyForecasts,
            temperatureConverter
        )
        given { temperatureConverter.fromKelvin(any()) }.willReturn(0.0)
        viewModel.start(city)
    }

    @Test
    fun `should show loading while getCurrentWeatherAndNext4DaysDailyForecasts is fetching`() {
        given { runBlocking { getCurrentWeatherAndNext4DaysDailyForecasts.run(eq(any())) } }.willReturn(Resource.Loading(null))

        viewModel.loadingForecasts.value shouldBe true
    }

    @Test
    fun `starting should get the current weather and the next 4 days forecasts`() {
        reset(getCurrentWeatherAndNext4DaysDailyForecasts)
        viewModel.start(city)

//        verify(getCurrentWeatherAndNext4DaysDailyForecasts).invoke(eq(GetCurrentWeatherAndNext4DaysDailyForecasts.Params(city)), any())
        runBlocking { verify(getCurrentWeatherAndNext4DaysDailyForecasts).run(GetCurrentWeatherAndNext4DaysDailyForecasts.Params(city)) }
    }

}