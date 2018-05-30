package com.example.andreromano.coolweather.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.andreromano.coolweather.ThreeHourForecast


@Database(entities = [
    ThreeHourForecast::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun threeHourForecastDao(): ThreeHourForecastDao
}