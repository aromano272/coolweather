package com.example.andreromano.coolweather.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.andreromano.coolweather.DailyForecast


@Database(entities = [
    DailyForecast::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyForecastDao(): DailyForecastDao
}