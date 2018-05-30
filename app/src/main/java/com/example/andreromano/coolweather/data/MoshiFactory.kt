package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.network.ToThreeHourForecastAdapter
import com.squareup.moshi.Moshi


object MoshiFactory {

    private val instance by lazy {
        val moshi = Moshi.Builder()
            .add(ToThreeHourForecastAdapter())
            .build()
        moshi
    }

    fun build(): Moshi = instance

}