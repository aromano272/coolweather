package com.example.andreromano.coolweather.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.andreromano.coolweather.DailyForecast


@Dao
interface DailyForecastDao {
    // We're not using LiveData because we use Kotlin Coroutines instead to run the UseCases
    @Query("SELECT * FROM dailyforecast")
    fun getAll(): List<DailyForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg forecast: DailyForecast)

}