package com.example.andreromano.coolweather.data


class FahrenheitTemperatureConverter : TemperatureConverter {
    override fun fromKelvin(kelvin: Double): Double {
        //° F = 9/5 (K - 273) + 32
        return (9.0/5.0) * (kelvin - 273) + 32
    }
}