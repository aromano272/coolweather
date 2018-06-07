package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.TemperatureConverter
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherByCity
import com.example.andreromano.coolweather.usecases.GetNext5DaysDailyForecasts
import org.joda.time.DateTime
import java.util.*
import kotlin.math.roundToInt

// FIXME: After some time of inactivity, the view state is restore, maybe through the platforms widgets(EditText, etc..)
// onRestoreInstance, but the ViewModel itself is not, resulting in a search input edittext filled with the previous data
// and a ViewModel with citiesSearchQuery as NULL, result in unsynced view state and viewmodel state
// maybe this will help: https://medium.com/google-developers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090
class DetailsViewModel(
    city: City,
    private val getCurrentWeatherByCity: GetCurrentWeatherByCity,
    private val getNext5DaysDailyForecasts: GetNext5DaysDailyForecasts,
    private val temperatureConverter: TemperatureConverter
) : ViewModel() {

    data class NextDaysForecastView(
        val dayOfWeekAbbr: String,
        val weatherConditionIcon: String,
        val temperature: Int,
        val temperatureUnit: String,
        val humidity: Int
    )

    fun start(city: City) {
        getCurrentWeatherByCity(GetCurrentWeatherByCity.Params(city)) { result ->
            when (result) {
                is Resource.Failure -> {
                    _loadingCurrentWeather.value = false
                }
                is Resource.Loading -> {
                    _loadingCurrentWeather.value = true
                    result.data?.let {
                        updateCurrentWeather(it)
                    }
                }
                is Resource.Success -> {
                    _loadingCurrentWeather.value = false
                    updateCurrentWeather(result.data)
                }
            }
        }
        getNext5DaysDailyForecasts(GetNext5DaysDailyForecasts.Params(city)) {
            when (it) {
                is Resource.Failure -> {
                    _loadingNextDaysForecasts.value = false
                }
                is Resource.Loading -> {
                    _loadingNextDaysForecasts.value = true
                    it.data?.let {
                        updateNextDaysForecasts(it)
                    }
                }
                is Resource.Success -> {
                    _loadingNextDaysForecasts.value = false
                    updateNextDaysForecasts(it.data)
                }
            }
        }
    }

    private fun updateNextDaysForecasts(forecasts: GetNext5DaysDailyForecasts.Next5DaysDailyForecasts) {
        _nextDay1View.value = forecasts.day1?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                it.averageTemperature.roundToInt(),
                "°",
                it.humidity
            )
        }
        _nextDay2View.value = forecasts.day2?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                it.averageTemperature.roundToInt(),
                "°",
                it.humidity
            )
        }
        _nextDay3View.value = forecasts.day3?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                it.averageTemperature.roundToInt(),
                "°",
                it.humidity
            )
        }
        _nextDay4View.value = forecasts.day4?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                it.averageTemperature.roundToInt(),
                "°",
                it.humidity
            )
        }
    }

    fun updateCurrentWeather(forecast: ThreeHourForecast) {
        _currentTemperature.value = temperatureConverter.fromKelvin(forecast.temperature).toInt()
        _temperatureUnit.value = "°" // TODO: make setting
    }

    val cityName = city.name
    val countryName = CountryUtils.iso2ToCountryName(city.countryIso2) ?: city.countryIso2

    private val _loadingCurrentWeather = MutableLiveData<Boolean>()
    val loadingCurrentWeather: LiveData<Boolean>
        get() = _loadingCurrentWeather

    private val _loadingNextDaysForecasts = MutableLiveData<Boolean>()
    val loadingNextDaysForecasts: LiveData<Boolean>
        get() = _loadingNextDaysForecasts

    private val _currentTemperature = MutableLiveData<Int>()
    val currentTemperature: LiveData<Int>
        get() = _currentTemperature

    private val _temperatureUnit = MutableLiveData<String>()
    val temperatureUnit: LiveData<String>
        get() = _temperatureUnit

    private val _nextDay1View = MutableLiveData<NextDaysForecastView>()
    val nextDay1View: LiveData<NextDaysForecastView>
        get() = _nextDay1View

    private val _nextDay2View = MutableLiveData<NextDaysForecastView>()
    val nextDay2View: LiveData<NextDaysForecastView>
        get() = _nextDay2View

    private val _nextDay3View = MutableLiveData<NextDaysForecastView>()
    val nextDay3View: LiveData<NextDaysForecastView>
        get() = _nextDay3View

    private val _nextDay4View = MutableLiveData<NextDaysForecastView>()
    val nextDay4View: LiveData<NextDaysForecastView>
        get() = _nextDay4View


    private val _currentWeatherByCity = MutableLiveData<Resource<ThreeHourForecast>>()
    val currentWeatherByCity: LiveData<Resource<ThreeHourForecast>>
        get() = _currentWeatherByCity

}