package com.example.andreromano.coolweather.network

import com.example.andreromano.coolweather.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.joda.time.DateTimeUtils
import org.junit.Before
import org.junit.Test


class MoshiAdapterTest {

    val moshi = Moshi.Builder()
        .add(ToThreeHourForecastAdapter())
        .build()

    val currentTimeMillis = 1337L

    val openWeather5Day3HourForecastResponseJson =
        """
{
   "cod":"200",
   "message":0.004,
   "cnt":40,
   "list":[
      {
         "dt":1527519600,
         "main":{
            "temp":291.79,
            "temp_min":291.199,
            "temp_max":291.79,
            "pressure":1026.76,
            "sea_level":1029.64,
            "grnd_level":1026.76,
            "humidity":83,
            "temp_kf":0.59
         },
         "weather":[
            {
               "id":802,
               "main":"Clouds",
               "description":"scattered clouds",
               "icon":"03d"
            }
         ],
         "clouds":{
            "all":32
         },
         "wind":{
            "speed":7.91,
            "deg":315.002
         },
         "rain":{

         },
         "sys":{
            "pod":"d"
         },
         "dt_txt":"2018-05-28 15:00:00"
      },
      {
         "dt":1527530400,
         "main":{
            "temp":291.44,
            "temp_min":290.994,
            "temp_max":291.44,
            "pressure":1026.91,
            "sea_level":1029.71,
            "grnd_level":1026.91,
            "humidity":83,
            "temp_kf":0.44
         },
         "weather":[
            {
               "id":800,
               "main":"Clear",
               "description":"clear sky",
               "icon":"01d"
            }
         ],
         "clouds":{
            "all":0
         },
         "wind":{
            "speed":8.26,
            "deg":322.001
         },
         "rain":{

         },
         "sys":{
            "pod":"d"
         },
         "dt_txt":"2018-05-28 18:00:00"
      },
      {
         "dt":1527541200,
         "main":{
            "temp":289.75,
            "temp_min":289.457,
            "temp_max":289.75,
            "pressure":1028.26,
            "sea_level":1031.01,
            "grnd_level":1028.26,
            "humidity":90,
            "temp_kf":0.3
         },
         "weather":[
            {
               "id":800,
               "main":"Clear",
               "description":"clear sky",
               "icon":"01n"
            }
         ],
         "clouds":{
            "all":0
         },
         "wind":{
            "speed":7.12,
            "deg":328
         },
         "rain":{

         },
         "sys":{
            "pod":"n"
         },
         "dt_txt":"2018-05-28 21:00:00"
      },
      {
         "dt":1527552000,
         "main":{
            "temp":288.48,
            "temp_min":288.336,
            "temp_max":288.48,
            "pressure":1028.45,
            "sea_level":1031.35,
            "grnd_level":1028.45,
            "humidity":98,
            "temp_kf":0.15
         },
         "weather":[
            {
               "id":500,
               "main":"Rain",
               "description":"light rain",
               "icon":"10n"
            }
         ],
         "clouds":{
            "all":48
         },
         "wind":{
            "speed":5.61,
            "deg":318.502
         },
         "rain":{
            "3h":0.005
         },
         "sys":{
            "pod":"n"
         },
         "dt_txt":"2018-05-29 00:00:00"
      },
      {
         "dt":1527562800,
         "main":{
            "temp":287.682,
            "temp_min":287.682,
            "temp_max":287.682,
            "pressure":1027.57,
            "sea_level":1030.38,
            "grnd_level":1027.57,
            "humidity":100,
            "temp_kf":0
         },
         "weather":[
            {
               "id":500,
               "main":"Rain",
               "description":"light rain",
               "icon":"10n"
            }
         ],
         "clouds":{
            "all":44
         },
         "wind":{
            "speed":5.16,
            "deg":313.504
         },
         "rain":{
            "3h":0.11
         },
         "sys":{
            "pod":"n"
         },
         "dt_txt":"2018-05-29 03:00:00"
      },
      {
         "dt":1527573600,
         "main":{
            "temp":287.504,
            "temp_min":287.504,
            "temp_max":287.504,
            "pressure":1027.58,
            "sea_level":1030.31,
            "grnd_level":1027.58,
            "humidity":100,
            "temp_kf":0
         },
         "weather":[
            {
               "id":500,
               "main":"Rain",
               "description":"light rain",
               "icon":"10n"
            }
         ],
         "clouds":{
            "all":44
         },
         "wind":{
            "speed":4.82,
            "deg":308.504
         },
         "rain":{
            "3h":0.01
         },
         "sys":{
            "pod":"d"
         },
         "dt_txt":"2018-05-29 06:00:00"
      },
      {
         "dt":1527584400,
         "main":{
            "temp":289.216,
            "temp_min":289.216,
            "temp_max":289.216,
            "pressure":1027.82,
            "sea_level":1030.55,
            "grnd_level":1027.82,
            "humidity":93,
            "temp_kf":0
         },
         "weather":[
            {
               "id":800,
               "main":"Clear",
               "description":"clear sky",
               "icon":"01d"
            }
         ],
         "clouds":{
            "all":0
         },
         "wind":{
            "speed":3.13,
            "deg":300.01
         },
         "rain":{

         },
         "sys":{
            "pod":"d"
         },
         "dt_txt":"2018-05-29 09:00:00"
      },
      {
         "dt":1527595200,
         "main":{
            "temp":290.558,
            "temp_min":290.558,
            "temp_max":290.558,
            "pressure":1027.3,
            "sea_level":1030.06,
            "grnd_level":1027.3,
            "humidity":88,
            "temp_kf":0
         },
         "weather":[
            {
               "id":800,
               "main":"Clear",
               "description":"clear sky",
               "icon":"01d"
            }
         ],
         "clouds":{
            "all":0
         },
         "wind":{
            "speed":3.81,
            "deg":263.5
         },
         "rain":{

         },
         "sys":{
            "pod":"d"
         },
         "dt_txt":"2018-05-29 12:00:00"
      }
   ],
   "city":{
      "id":8013113,
      "name":"Almada",
      "coord":{
         "lat":38.6789,
         "lon":-9.1625
      },
      "country":"PT"
   }
}
    """


    @Before
    fun setup() {
        DateTimeUtils.setCurrentMillisFixed(currentTimeMillis)
    }

    @Test
    fun `weather 5 days 3 hours forecast success response`() {
        val expectedResponse = OpenWeather5Day3HourForecastResponse(
            city = OpenWeather5Day3HourForecastResponse.City(
                id = 8013113,
                name = "Almada",
                countryIso2 = "PT"
            ),
            list = listOf(
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527519600,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 291.79,
                        humidity = 83
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 802,
                            icon = "03d"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527530400,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 291.44,
                        humidity = 83
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 800,
                            icon = "01d"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527541200,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 289.75,
                        humidity = 90
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 800,
                            icon = "01n"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527552000,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 288.48,
                        humidity = 98
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 500,
                            icon = "10n"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527562800,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 287.682,
                        humidity = 100
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 500,
                            icon = "10n"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527573600,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 287.504,
                        humidity = 100
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 500,
                            icon = "10n"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527584400,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 289.216,
                        humidity = 93
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 800,
                            icon = "01d"
                        )
                    )
                ),
                OpenWeather5Day3HourForecastResponse.WeatherEntry(
                    timeSeconds = 1527595200,
                    main = OpenWeather5Day3HourForecastResponse.WeatherEntry.Main(
                        average = 290.558,
                        humidity = 88
                    ),
                    weather = listOf(
                        OpenWeather5Day3HourForecastResponse.WeatherEntry.Weather(
                            id = 800,
                            icon = "01d"
                        )
                    )
                )
            )
        )

        val actualResponse = moshi.adapter(OpenWeather5Day3HourForecastResponse::class.java).fromJson(openWeather5Day3HourForecastResponseJson)

        assertThat(actualResponse, `is`(expectedResponse))
    }

    @Test
    fun `weather 5 days 3 hours forecast success response to list of daily forecasts`() {
        val expected = listOf(
            ThreeHourForecast(
                forecastDateMillis = 1527519600 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 291.79,
                humidity = 83,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 802,
                        icon = "03d"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527530400 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 291.44,
                humidity = 83,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 800,
                        icon = "01d"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527541200 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 289.75,
                humidity = 90,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 800,
                        icon = "01n"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527552000 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 288.48,
                humidity = 98,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 500,
                        icon = "10n"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527562800 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 287.682,
                humidity = 100,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 500,
                        icon = "10n"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527573600 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 287.504,
                humidity = 100,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 500,
                        icon = "10n"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527584400 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 289.216,
                humidity = 93,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 800,
                        icon = "01d"
                    )
                )
            ),
            ThreeHourForecast(
                forecastDateMillis = 1527595200 * 1000L,
                lastUpdatedMillis = currentTimeMillis,
                city = City(
                    id = 8013113,
                    name = "Almada",
                    countryIso2 = "PT"
                ),
                temperature = 290.558,
                humidity = 88,
                weatherConditions = listOf(
                    WeatherCondition(
                        id = 800,
                        icon = "01d"
                    )
                )
            )
        )

        val type = Types.newParameterizedType(List::class.java, ThreeHourForecast::class.java)
        val actualResponse = moshi.adapter<List<ThreeHourForecast>>(type).fromJson(openWeather5Day3HourForecastResponseJson)

        assertThat(actualResponse, `is`(expected))
    }

}