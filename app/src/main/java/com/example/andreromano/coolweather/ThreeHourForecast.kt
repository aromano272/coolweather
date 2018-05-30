package com.example.andreromano.coolweather

import android.arch.persistence.room.*
import com.example.andreromano.coolweather.data.RoomTypeConverters


@Entity(
    indices = [
        Index(value = ["forecastDateMillis", "city_id"], unique = true)
    ]
)
data class ThreeHourForecast(
    val forecastDateMillis: Long,
    val lastUpdatedMillis: Long,
    @Embedded(prefix = "city_")
    val city: City,
    val temperature: Double,
    @field:TypeConverters(RoomTypeConverters::class)
    val weatherConditions: List<WeatherCondition>,
    val humidity: Int // 0-100
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}