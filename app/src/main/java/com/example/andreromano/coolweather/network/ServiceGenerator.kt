package com.example.andreromano.coolweather.network


import com.example.andreromano.coolweather.BuildConfig
import com.example.andreromano.coolweather.data.MoshiFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Resources:
// http://stackoverflow.com/questions/22490057/android-okhttp-with-basic-authentication
// https://futurestud.io/tutorials/android-basic-authentication-with-retrofit
// https://medium.com/@sreekumar_av/certificate-public-key-pinning-in-android-using-retrofit-2-0-74140800025b#.a1mq4v507
// https://github.com/square/okhttp/wiki/HTTPS

object ServiceGenerator {
    val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder().baseUrl(API_BASE_URL)

        val httpClient = OkHttpClient.Builder()

        val moshi = MoshiFactory.build()

        builder.addConverterFactory(MoshiConverterFactory.create(moshi))

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        val client = httpClient.build()

        builder
            .client(client)
            .build()
    }

    private val API_BASE_URL = "http://www.base.com"

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)

}