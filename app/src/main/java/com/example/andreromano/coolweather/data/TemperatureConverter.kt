package com.example.andreromano.coolweather.data


interface TemperatureConverter {
    fun fromKelvin(kelvin: Double): Double
}