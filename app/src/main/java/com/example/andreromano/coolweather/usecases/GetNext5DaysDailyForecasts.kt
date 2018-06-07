package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.DailyForecast
import com.example.andreromano.coolweather.Resource
import com.example.andreromano.coolweather.ResourceUseCase
import com.example.andreromano.coolweather.data.ForecastsRepository
import org.joda.time.DateTime


class GetNext5DaysDailyForecasts(
    private val forecastsRepository: ForecastsRepository
) : ResourceUseCase<GetNext5DaysDailyForecasts.Next5DaysDailyForecasts, GetNext5DaysDailyForecasts.Params>() {

    data class Params(val city: City)

    data class Next5DaysDailyForecasts(
        val day1: DailyForecast?,
        val day2: DailyForecast?,
        val day3: DailyForecast?,
        val day4: DailyForecast?,
        val day5: DailyForecast?
    )

    override fun run(params: Params): Resource<Next5DaysDailyForecasts> {
        val now = DateTime.now()
        val firstDayMin = now.plusDays(1).withTimeAtStartOfDay().millis
        val lastDayMax = now.plusDays(5).millisOfDay().withMaximumValue().millis
        val forecasts = forecastsRepository.getForecastsByCity(
            city = params.city,
            from = firstDayMin,
            to = lastDayMax
        )

        return when (forecasts) {
            is Resource.Failure -> Resource.Failure(null, forecasts.error)
            is Resource.Success -> {
                val dailyForecastsMap = forecasts.data
                    .sortedBy { it.forecastDateMillis }
                    .groupBy { DateTime(it.forecastDateMillis).withTimeAtStartOfDay().millis }
                    .mapValues { startOfDayToForecasts ->
                        val startOfDay = startOfDayToForecasts.key
                        val forecasts = startOfDayToForecasts.value
                        val temperatures = forecasts.map { it.temperature }

                        DailyForecast(
                            startOfDay,
                            forecasts.map { it.lastUpdatedMillis }.min()!!,
                            params.city,
                            temperatures.average(),
                            temperatures.min()!!,
                            temperatures.max()!!,
                            forecasts.flatMap { it.weatherConditions }.maxBy { it.id }!!,
                            forecasts.map { it.humidity }.max()!!
                        )
                    }

                val next5DaysForecasts = Next5DaysDailyForecasts(
                    dailyForecastsMap[now.plusDays(1).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(2).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(3).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(4).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(5).withTimeAtStartOfDay().millis]
                )

                if (dailyForecastsMap.isNotEmpty()) Resource.Success(next5DaysForecasts)
                else Resource.Failure(null, Error("Couldn't find current weather information"))
            }
            is Resource.Loading -> throw NotImplementedError() // TODO: Implement
        }
    }
}