package com.example.andreromano.coolweather.data


class CelsiusTemperatureConverter : TemperatureConverter {
    override fun fromKelvin(kelvin: Double): Double {
        //° C = K - 273
        return kelvin - 273
    }
}