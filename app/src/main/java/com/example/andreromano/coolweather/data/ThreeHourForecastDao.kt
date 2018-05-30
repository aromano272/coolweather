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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg forecast: ThreeHourForecast)

}