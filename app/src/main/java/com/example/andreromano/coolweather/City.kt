package com.example.andreromano.coolweather

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class City(
    val id: Int,
    val name: String,
    @Json(name = "country")
    val countryIso2: String
) : Parcelable