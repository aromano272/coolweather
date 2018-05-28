package com.example.andreromano.coolweather.data

import android.arch.persistence.room.TypeConverter
import com.example.andreromano.coolweather.WeatherCondition
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types


object RoomTypeConverters {

    private val moshi by lazy {
        MoshiFactory.build()
    }

    @TypeConverter
    @JvmStatic
    fun fromListOfWeatherConditionsToJson(conditions: List<WeatherCondition>): String {
        val type = Types.newParameterizedType(List::class.java, WeatherCondition::class.java)
        val adapter: JsonAdapter<List<WeatherCondition>> = moshi.adapter(type)
        return adapter.toJson(conditions)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonToListOfWeatherConditions(json: String): List<WeatherCondition> {
        val type = Types.newParameterizedType(List::class.java, WeatherCondition::class.java)
        val adapter: JsonAdapter<List<WeatherCondition>> = moshi.adapter(type)
        return adapter.fromJson(json) ?: emptyList()
    }

}