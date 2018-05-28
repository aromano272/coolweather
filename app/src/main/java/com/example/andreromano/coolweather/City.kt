package com.example.andreromano.coolweather

import android.arch.persistence.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val id: Int,
    val name: String,
    @Json(name = "country")
    val countryIso2: String
)