package com.example.andreromano.coolweather.network

import com.example.andreromano.coolweather.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime

//@Retention(AnnotationRetention.RUNTIME)
//@JsonQualifier
//annotation class ToDailyForecast
//
//class ToDailyForecastAdapter {
//    @FromJson
////    @ToDailyForecast
//    fun fromJson(response: OpenWeather5Day3HourForecastResponse): List<DailyForecast> = response.list.map { item ->
//        val date = DateTime(item.timeSeconds * 1000L)
//        // we standardize the forecastDateMillis to always be the unix at the start of the day, so
//        // we easily identify duplicate forecasts
//        val startOfDayMillis = date.withTimeAtStartOfDay().millis
//
//        DailyForecast(
//            forecastDateMillis = startOfDayMillis,
//            lastUpdatedMillis = DateTime.now().millis,
//            city = City(
//                id = response.city.id,
//                name = response.city.name,
//                countryIso2 = response.city.countryIso2
//            ),
//            temperature = item.main.average,
//            minTemperature = item.main.min,
//            maxTemperature = item.main.max,
//            weatherConditions = item.weather.map { condition -> WeatherCondition(
//                id = condition.id,
//                icon = condition.icon
//            )},
//            humidity = item.main.humidity
//        )
//    }
//
//    @ToJson
//    fun toJson(/*@ToDailyForecast */value: DailyForecast): Any? {
//        throw UnsupportedOperationException()
//    }
//}

class ToThreeHourForecastAdapter {
    @FromJson
    fun fromJson(response: OpenWeather5Day3HourForecastResponse): List<ThreeHourForecast> = response.list.map { item ->
        ThreeHourForecast(
            forecastDateMillis = item.timeSeconds * 1000L,
            lastUpdatedMillis = DateTime.now().millis,
            city = City(
                id = response.city.id,
                name = response.city.name,
                countryIso2 = response.city.countryIso2
            ),
            temperature = item.main.average,
            weatherConditions = item.weather.map { condition -> WeatherCondition(
                id = condition.id,
                icon = condition.icon
            )},
            humidity = item.main.humidity
        )
    }

    @ToJson
    fun toJson(value: ThreeHourForecast): Any? {
        throw UnsupportedOperationException()
    }
}
