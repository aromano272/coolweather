package com.example.andreromano.coolweather

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class WeatherCondition(
    val id: Int,
    val icon: String
)