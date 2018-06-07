package com.example.andreromano.coolweather


data class DailyForecast(
    val forecastDateMillis: Long,
    val lastUpdatedMillis: Long,
    val city: City,
    val averageTemperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val weatherCondition: WeatherCondition,
    val humidity: Int // 0-100
)