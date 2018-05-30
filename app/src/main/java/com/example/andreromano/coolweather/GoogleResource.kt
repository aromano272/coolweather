package com.example.andreromano.coolweather

class GoogleResource<out T> private constructor(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    companion object {

        fun <T> success(data: T): GoogleResource<T> {
            return GoogleResource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): GoogleResource<T> {
            return GoogleResource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): GoogleResource<T> {
            return GoogleResource(Status.LOADING, data, null)
        }
    }
}
