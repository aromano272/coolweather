package com.example.andreromano.coolweather.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.andreromano.coolweather.ThreeHourForecast


@Dao
interface ThreeHourForecastDao {
    // We're not using LiveData because we use Kotlin Coroutines instead to run the UseCases
    // TODO: Maybe we should use LiveData to implement the Single Source of Truth pattern
    @Query("SELECT * FROM threehourforecast")
    fun getAll(): List<ThreeHourForecast>

    @Query("SELECT * FROM threehourforecast WHERE city_id = :cityId")
    fun getByCity(cityId: Int): List<ThreeHourForecast>

    @Query("""
        SELECT * FROM threehourforecast
        WHERE
            city_id = :cityId AND
            forecastDateMillis >= :from AND
            forecastDateMillis <= :to
    """)
    fun getByCity(cityId: Int, from: Long, to: Long): List<ThreeHourForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg forecast: ThreeHourForecast)

}