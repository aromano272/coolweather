package com.example.andreromano.coolweather


sealed class Resource<out DataType> {
    data class Loading<out DataType>(val data: DataType?) : Resource<DataType>()

    data class Success<out DataType>(val data: DataType) : Resource<DataType>()

    data class Failure<out DataType>(val data: DataType?, val error: Error) : Resource<DataType>()
}