package com.example.andreromano.coolweather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenWeather5Day3HourForecastResponse(
    val city: City,
    val list: List<WeatherEntry>
) {
    @JsonClass(generateAdapter = true)
    data class City(
        val id: Int,
        val name: String,
        @Json(name = "country")
        val countryIso2: String
    )
    @JsonClass(generateAdapter = true)
    data class WeatherEntry(
        @Json(name = "dt")
        val timeSeconds: Int,
        val main: Main,
        val weather: List<Weather>
    ) {
        @JsonClass(generateAdapter = true)
        data class Main(
            @Json(name = "temp")
            val average: Double,
            val humidity: Int // 0-100
        )
        @JsonClass(generateAdapter = true)
        data class Weather(
            val id: Int,
            val icon: String
        )
    }
}