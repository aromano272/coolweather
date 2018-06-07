package com.example.andreromano.coolweather.network

import retrofit2.Retrofit


class OpenWeatherService(retrofit: Retrofit) {

    private val api by lazy { retrofit.create(ApiRequests.OpenWeather::class.java) }

    fun get5Day3HourForecasts(cityId: Int) = api.openWeather5Day3HourForecast(cityId)

}