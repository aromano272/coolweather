package com.example.andreromano.coolweather

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.andreromano.coolweather.data.AppDatabase
import com.example.andreromano.coolweather.data.ThreeHourForecastDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.*
import org.junit.Ignore

@RunWith(AndroidJUnit4::class)
class SimpleRoomTest {

    private lateinit var threeHourForecastDao: ThreeHourForecastDao
    private lateinit var db: AppDatabase

    private val forecast = ThreeHourForecast(
        1337L,
        1337L,
        City(1337, "Lisbon", "PT"),
        10.0,
        listOf(
            WeatherCondition(1, "ICON")
        ),
        10
    )

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        threeHourForecastDao = db.threeHourForecastDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun inserting_forecast_should_correctly_marshal_and_unmarshal_it() {
        threeHourForecastDao.insert(forecast)

        val forecasts = threeHourForecastDao.getAll()
        assertThat(forecasts[0], `is`(equalTo(forecast)))
    }

    @Test
    fun inserting_forecast_with_the_same_forecastDateMillis_and_the_same_city_id_should_replace_the_existing_value() {
        val newForecast = forecast.copy(temperature = 123.123)
        threeHourForecastDao.insert(forecast)
        threeHourForecastDao.insert(newForecast)

        val forecasts = threeHourForecastDao.getAll()

        assertThat(forecasts[0], `is`(equalTo(newForecast)))
        assertThat(forecasts.size, `is`(1))
    }

    @Ignore
    fun finding_city_by_name_should_return_the_correct_ones() {
        val lisbon = City(1, "Lisbon", "PT")
        val oporto = City(2, "Oporto", "PT")
        val barcelona = City(3, "Barcelona", "ES")
        val lisbonForecast = forecast.copy(city = lisbon)
        val oportoForecast = forecast.copy(city = oporto)
        val barcelonaForecast = forecast.copy(city = barcelona)

        threeHourForecastDao.insert(lisbonForecast, oportoForecast, barcelonaForecast)
    }

}