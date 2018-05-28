package com.example.andreromano.coolweather


interface FileReader {

    fun readFile(fileName: String): ByteArray?

}