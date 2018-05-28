package com.example.andreromano.coolweather.ui

import android.arch.lifecycle.*
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Either
import com.example.andreromano.coolweather.data.CitiesRepository


class TestViewModel : ViewModel {

    constructor() : super() {
        charQuery.value = ""
    }

    private var counterSuccess: Int = 0
    private var counterFailure: Int = 0

    private val _success = MutableLiveData<String>()
    val success: LiveData<String>
        get() = _success

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String>
        get() = _failure

    fun successClicked() {
        counterSuccess++
        _success.postValue(counterSuccess.toString())
    }

    fun failureClicked() {
        counterFailure++
        _failure.postValue(counterFailure.toString())
    }

    private val _charCount = MutableLiveData<Int>()
    val charCount: LiveData<Int>
        get() = _charCount

    private val _charCountError = MutableLiveData<String?>()
    val charCountError: LiveData<String?>
        get() = _charCountError

    private val charQuery = MutableLiveData<String>()

    val mapCharCount: LiveData<Int> = Transformations.map(charQuery, { newText ->
        newText.length
    })

    val mapCharCountError: LiveData<String?> = Transformations.map(charQuery, { newText ->
        if (newText.length % 3 == 0) "Error" else null
    })

    val switchCharCount: LiveData<Int> = Transformations.switchMap(charQuery, { newText ->
        val liveData = MutableLiveData<Int>()
        liveData.postValue(newText.length)
        liveData
    })

    val switchCharCountError: LiveData<String?> = Transformations.switchMap(charQuery, { newText ->
        val liveData = MutableLiveData<String?>()
        liveData.postValue(if (newText.length % 3 == 0) "Error" else null)
        liveData
    })

    private val charQuery_Forever: MutableLiveData<String> by lazy {
        val liveData = MutableLiveData<String>()
        liveData.observeForever { newText ->
            _charCount.postValue(newText!!.length)
            if (newText!!.length % 3 == 0) {
                _charCountError.postValue("Error")
            } else {
                _charCountError.postValue(null)
            }
        }
        liveData
    }

    fun onFreeTypeTextChanged(newText: String) {
        charQuery.postValue(newText)
        return
        _charCount.postValue(newText.length)
        if (newText.length % 3 == 0) {
            _charCountError.postValue("Error")
        } else {
            _charCountError.postValue(null)
        }
    }

}