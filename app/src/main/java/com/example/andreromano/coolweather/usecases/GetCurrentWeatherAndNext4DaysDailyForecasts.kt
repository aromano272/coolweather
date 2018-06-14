package com.example.andreromano.coolweather.usecases

import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.ForecastsRepository
import net.danlew.android.joda.DateUtils
import org.joda.time.DateTime


class GetCurrentWeatherAndNext4DaysDailyForecasts(
    private val forecastsRepository: ForecastsRepository
) : ResourceUseCase<GetCurrentWeatherAndNext4DaysDailyForecasts.Next5DaysDailyForecasts, GetCurrentWeatherAndNext4DaysDailyForecasts.Params>() {

    data class Params(val city: City)

    data class Next5DaysDailyForecasts(
        val today: ThreeHourForecast?,
        val todayPlus1: DailyForecast?,
        val todayPlus2: DailyForecast?,
        val todayPlus3: DailyForecast?,
        val todayPlus4: DailyForecast?
    )

    override fun run(params: Params): Resource<Next5DaysDailyForecasts> {
        val now = DateTime.now()
        val firstDayMin = now.withTimeAtStartOfDay().millis
        val lastDayMax = now.plusDays(4).millisOfDay().withMaximumValue().millis
        val forecasts = forecastsRepository.getForecastsByCity(
            city = params.city,
            from = firstDayMin,
            to = lastDayMax
        )

        return when (forecasts) {
            is Resource.Failure -> Resource.Failure(null, forecasts.error)
            is Resource.Success -> {

                val (today, nextDays) = forecasts.data.partition {
                    DateUtils.isToday(DateTime(it.forecastDateMillis))
                }

                val todaysCurrentForecast = today
                    .sortedBy { forecast -> Math.abs(now.millis - forecast.forecastDateMillis) } // we get the closest to our current time
                    .firstOrNull()

                val dailyForecastsMap = nextDays
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
                    todaysCurrentForecast,
                    dailyForecastsMap[now.plusDays(1).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(2).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(3).withTimeAtStartOfDay().millis],
                    dailyForecastsMap[now.plusDays(4).withTimeAtStartOfDay().millis]
                )

                if (todaysCurrentForecast == null && dailyForecastsMap.isEmpty()) Resource.Failure(null, Error("Couldn't find current weather information"))
                else Resource.Success(next5DaysForecasts)
            }
            is Resource.Loading -> throw NotImplementedError() // TODO: Implement
        }
    }
}