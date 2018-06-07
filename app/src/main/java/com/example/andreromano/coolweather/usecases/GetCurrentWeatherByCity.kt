package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.ForecastsRepository
import org.joda.time.DateTime


class GetCurrentWeatherByCity(
    private val forecastsRepository: ForecastsRepository
) : ResourceUseCase<ThreeHourForecast, GetCurrentWeatherByCity.Params>() {

    data class Params(val city: City)

    override fun run(params: Params): Resource<ThreeHourForecast> {
        val now = DateTime.now()
        val todayMin = now.millisOfDay().withMaximumValue().millis
        val todayMax = now.millisOfDay().withMaximumValue().millis
        val forecasts = forecastsRepository.getForecastsByCity(params.city, todayMin, todayMax)

        return when (forecasts) {
            is Resource.Failure -> Resource.Failure(null, forecasts.error)
            is Resource.Success -> {
                val forecast = forecasts.data
                    .filter { forecast -> forecast.forecastDateMillis < todayMax } // we dont want future forecasts
                    .sortedBy { forecast -> Math.abs(now.millis - forecast.forecastDateMillis) } // we get the closest to our current time
                    .firstOrNull()

                if (forecast != null) Resource.Success(forecast)
                else Resource.Failure(null, Error("Couldn't find current weather information"))
            }
            is Resource.Loading -> throw NotImplementedError() // TODO: Implement
        }
    }

}