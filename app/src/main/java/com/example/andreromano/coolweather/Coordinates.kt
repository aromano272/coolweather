package com.example.andreromano.coolweather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(
    val lon: Double,
    val lat: Double
)