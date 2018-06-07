package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.network.OpenWeatherService


class ForecastsRemoteDataSource(
    private val openWeatherService: OpenWeatherService
) : ForecastsDataSource {

    override fun getForecastsByCity(city: City, from: Long?, to: Long?): Resource<List<ThreeHourForecast>> {
        return try {
            val response = openWeatherService.get5Day3HourForecasts(city.id).execute()
            when (response.isSuccessful) {
                true -> Resource.Success(response.body()!!) // TODO: Remove !! and handle null value
                false -> Resource.Failure(null, Error("Not implemented")) // TODO: Implement
            }
        } catch (exception: Exception) {
            Resource.Failure(null, Error(exception.localizedMessage))
        }
    }

}