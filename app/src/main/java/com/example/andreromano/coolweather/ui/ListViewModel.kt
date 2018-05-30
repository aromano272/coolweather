package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.ThreeHourForecast
import com.example.andreromano.coolweather.Either
import com.example.andreromano.coolweather.network.ApiRequests
import com.example.andreromano.coolweather.network.ServiceGenerator
import com.example.andreromano.coolweather.usecases.SearchCityByName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// FIXME: After some time of inactivity, the view state is restore, maybe through the platforms widgets(EditText, etc..)
// onRestoreInstance, but the ViewModel itself is not, resulting in a search input edittext filled with the previous data
// and a ViewModel with citiesSearchQuery as NULL, result in unsynced view state and viewmodel state
// maybe this will help: https://medium.com/google-developers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090
class ListViewModel(
    private val searchCityByName: SearchCityByName
) : ViewModel() {

    private val citiesSearchQuery = MutableLiveData<String>()

    fun onSearchQueryChanged(newText: String) {
        citiesSearchQuery.value = newText
    }

    fun onSearchClicked(view: View) {
        val searchQuery: String = citiesSearchQuery.value ?: return
        if (searchQuery.isBlank()) return

        _loadingSearchResults.value = true
        searchCityByName(SearchCityByName.Params(searchQuery), { result ->
            _loadingSearchResults.value = false
            when (result) {
                is Either.Success -> _citiesSearchResults.value = result.success
                is Either.Failure -> _citiesSearchError.value = result.error
            }
        })
    }

    private val _citiesSearchResults = MutableLiveData<List<City>>()
    val citiesSearchResults: LiveData<List<City>>
        get() = _citiesSearchResults

    // TODO: Implement this as a snack bar with the SingleEventLiveData or Event Wrapper
    // https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private val _citiesSearchError = MutableLiveData<Error>()
    val citiesSearchError: LiveData<Error>
        get() = _citiesSearchError

    val citiesSearchResultsString: LiveData<String?> = Transformations.map(citiesSearchResults, { cities ->
        cities.map { city -> city.name }.joinToString(separator = "\n")
    })

    private val _loadingSearchResults = MutableLiveData<Boolean>()
    val loadingSearchResults: LiveData<Boolean>
        get() = _loadingSearchResults

    fun onCityClicked(city: City) {
        val service = ServiceGenerator.createService(ApiRequests.OpenWeather::class.java)
        val call = service.openWeather5Day3HourForecast(city.id)
        call.enqueue(object : Callback<List<ThreeHourForecast>> {
            override fun onResponse(call: Call<List<ThreeHourForecast>>, response: Response<List<ThreeHourForecast>>) {
                if (response.isSuccessful) {
                    val cneas = response.body()
                    Log.e("DEBUG", cneas.toString())
                }
            }

            override fun onFailure(call: Call<List<ThreeHourForecast>>, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

}