package com.example.andreromano.coolweather

// taken from https://fernandocejas.com/2018/05/07/architecting-android-reloaded/

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(
    val messageError: String?
) {
    class NetworkConnection(message: String? = "Network Failure"): Failure(message)
    class ServerFailure(message: String? = "Server Failure"): Failure(message)
    class IOFailure(message: String): Failure(message)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(message: String): Failure(message)
}