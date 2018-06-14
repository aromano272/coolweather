package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.andreromano.coolweather.*
import com.example.andreromano.coolweather.data.TemperatureConverter
import com.example.andreromano.coolweather.usecases.GetCurrentWeatherAndNext4DaysDailyForecasts
import org.joda.time.DateTime
import java.util.*

// FIXME: After some time of inactivity, the view state is restore, maybe through the platforms widgets(EditText, etc..)
// onRestoreInstance, but the ViewModel itself is not, resulting in a search input edittext filled with the previous data
// and a ViewModel with citiesSearchQuery as NULL, result in unsynced view state and viewmodel state
// maybe this will help: https://medium.com/google-developers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090
class DetailsViewModel(
    city: City,
    private val getCurrentWeatherAndNext4DaysDailyForecasts: GetCurrentWeatherAndNext4DaysDailyForecasts,
    private val temperatureConverter: TemperatureConverter
) : ViewModel() {

    data class CurrentForecastView(
        val weatherConditionIcon: String,
        val temperature: Int
    )

    data class NextDaysForecastView(
        val dayOfWeekAbbr: String,
        val weatherConditionIcon: String,
        val temperature: Int,
        val humidity: Int
    )

    fun start(city: City) {
        getCurrentWeatherAndNext4DaysDailyForecasts.execute(GetCurrentWeatherAndNext4DaysDailyForecasts.Params(city)) {
            when (it) {
                is Resource.Failure -> {
                    _loadingForecasts.value = false
                }
                is Resource.Loading -> {
                    _loadingForecasts.value = true
                    it.data?.let {
                        updateForecasts(it)
                    }
                }
                is Resource.Success -> {
                    _loadingForecasts.value = false
                    updateForecasts(it.data)
                }
            }
        }
    }

    private fun updateForecasts(forecasts: GetCurrentWeatherAndNext4DaysDailyForecasts.Next5DaysDailyForecasts) {
        _currentWeatherView.value = forecasts.today?.let {
            CurrentForecastView(
                it.weatherConditions[0].icon,
                temperatureConverter.fromKelvin(it.temperature).toInt()
            )
        }
        _nextDay1View.value = forecasts.todayPlus1?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                temperatureConverter.fromKelvin(it.averageTemperature).toInt(),
                it.humidity
            )
        }
        _nextDay2View.value = forecasts.todayPlus2?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                temperatureConverter.fromKelvin(it.averageTemperature).toInt(),
                it.humidity
            )
        }
        _nextDay3View.value = forecasts.todayPlus3?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                temperatureConverter.fromKelvin(it.averageTemperature).toInt(),
                it.humidity
            )
        }
        _nextDay4View.value = forecasts.todayPlus4?.let {
            NextDaysForecastView(
                DateTime(it.forecastDateMillis).dayOfWeek().getAsText(Locale.getDefault()).take(3),
                it.weatherCondition.icon,
                temperatureConverter.fromKelvin(it.averageTemperature).toInt(),
                it.humidity
            )
        }
    }

    val cityName = city.name
    val countryName = CountryUtils.iso2ToCountryName(city.countryIso2) ?: city.countryIso2

    private val _loadingForecasts = MutableLiveData<Boolean>()
    val loadingForecasts: LiveData<Boolean>
        get() = _loadingForecasts

    private val _currentWeatherView = MutableLiveData<CurrentForecastView>()
    val currentWeatherView: LiveData<CurrentForecastView>
        get() = _currentWeatherView

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

}