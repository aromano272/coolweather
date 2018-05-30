package com.example.andreromano.coolweather.network

import com.example.andreromano.coolweather.BuildConfig
import com.example.andreromano.coolweather.ThreeHourForecast
import retrofit2.Call
import retrofit2.http.*


interface ApiRequests {

    interface OpenWeather {

        @GET("http://api.openweathermap.org/data/2.5/forecast?APPID=${BuildConfig.OpenWeatherApiKey}")
        fun openWeather5Day3HourForecast(@Query("id") cityId: Int): Call<List<ThreeHourForecast>>

    }

}