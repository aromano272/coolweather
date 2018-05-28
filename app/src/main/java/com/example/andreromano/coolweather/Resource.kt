package com.example.andreromano.coolweather

class Resource<out T> private constructor(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
