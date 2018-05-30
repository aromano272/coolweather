package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.ForecastsRepository
import org.joda.time.DateTime


class GetCurrentWeatherByCity(
    private val forecastsRepository: ForecastsRepository
) : ResourceUseCase<ThreeHourForecast, GetCurrentWeatherByCity.Params>() {

    data class Params(val city: City)

    override fun run(params: Params): Resource<ThreeHourForecast> {
        val forecasts = forecastsRepository.getForecastsByCity(params.city)
        val now = DateTime().millis

        return when (forecasts) {
            is Resource.Failure -> Resource.Failure(null, forecasts.error)
            is Resource.Success -> {
                val forecast = forecasts.data
                    .filter { forecast -> forecast.forecastDateMillis < now } // we dont want future forecasts
                    .sortedBy { forecast -> forecast.forecastDateMillis } // oldest to newest
                    .lastOrNull()

                if (forecast != null) Resource.Success(forecast)
                else Resource.Failure(null, Error("Couldn't find current weather information"))
            }
            is Resource.Loading -> throw NotImplementedError() // TODO: Implement
        }
    }

}