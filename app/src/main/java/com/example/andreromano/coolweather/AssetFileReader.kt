package com.example.andreromano.coolweather

import android.content.Context
import java.io.IOException


class AssetFileReader(private val context: Context) : FileReader {

    override fun readFile(fileName: String): ByteArray? {
        try {
            val inputStream = context.assets.open(fileName)
            val bytes = inputStream.use {
                it.readBytes()
            }

            return bytes
        } catch (ex: IOException) {
            return null
        }
    }

}