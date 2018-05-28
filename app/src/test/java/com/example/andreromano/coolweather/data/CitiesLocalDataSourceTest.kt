package com.example.andreromano.coolweather.data

import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.Coordinates
import com.example.andreromano.coolweather.Either
import com.example.andreromano.coolweather.FileReader
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitiesLocalDataSourceTest {

    val moshi = Moshi.Builder().build()

    val json = """
    [
        {
            "id": 707860,
            "name": "Lisbón",
            "country": "UA",
            "coord": {
                "lon": 34.283333,
                "lat": 44.549999
            }
        },
        {
            "id": 519188,
            "name": "Lisbãr",
            "country": "RU",
            "coord": {
                "lon": 37.666668,
                "lat": 55.683334
            }
        },
        {
            "id": 519189,
            "name": "Alisbon",
            "country": "RU",
            "coord": {
                "lon": 37.666669,
                "lat": 55.683339
            }
        }
    ]
    """.trimIndent()

    val cityLisbon = City(
        id = 707860,
        name = "Lisbón",
        countryIso2 = "UA"
    )

    val cityLisbar = City(
        id = 519188,
        name = "Lisbãr",
        countryIso2 = "RU"
    )

    val cityAlisbon = City(
        id = 519189,
        name = "Alisbon",
        countryIso2 = "RU"
    )

    val cities = listOf(
        cityLisbon,
        cityLisbar,
        cityAlisbon
    )

    private lateinit var dataSource: CitiesLocalDataSource

    @Mock
    private lateinit var reader: FileReader

    @Before
    fun setUp() {
        dataSource = CitiesLocalDataSource(reader)
    }

    @Test
    fun `moshi converter should be able to convert the bytearray into a list of cities`() {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, City::class.java)
        val adapter: JsonAdapter<List<City>> = moshi.adapter(type)


        val expected = cities
        val actual = adapter.fromJson(json)
        actual shouldEqual expected
    }

    @Test
    fun `should get data from reader if cache is not available`() {
        val byteArray = json.toByteArray()
        given { reader.readFile(any()) }.willReturn(byteArray)
        dataSource.cache = null

        dataSource.getCitiesByName("TEST")
        verify(reader).readFile(any())
    }

    @Test
    fun `should return cache if its available instead of going to the filereader`() {
        dataSource.cache = cities

        val result = dataSource.getCitiesByName("TEST")

        result shouldBeInstanceOf Either.Success::class.java
        verify(reader, never()).readFile("TEST")
    }

    @Test
    fun `when it fetches from local it should save it as cache`() {
        dataSource.cache = null

        given { reader.readFile(any()) }.willReturn(json.toByteArray())

        dataSource.getCitiesByName("TEST")
        verify(reader).readFile(any())
        dataSource.cache shouldEqual cities
    }

    @Test
    fun `search city by name should filter the list in a case insensitive manner`() {
        dataSource.cache = cities
        val expected = listOf(cityAlisbon)

        dataSource.getCitiesByName("alisbon") shouldEqual Either.Success(expected)
    }

    @Test
    fun `search city by name should filter the list for names only starting with the search query`() {
        dataSource.cache = cities
        val expected = listOf(cityLisbon, cityLisbar)

        dataSource.getCitiesByName("lisb") shouldEqual Either.Success(expected)
    }

    @Test
    fun `search city by name should ignore accents in the data source when filtering`() {
        dataSource.cache = cities
        val expected = listOf(cityLisbon)

        dataSource.getCitiesByName("lisbon") shouldEqual Either.Success(expected)
    }

    @Test
    fun `search city by name should ignore accents search query when filtering`() {
        dataSource.cache = cities
        val expected = listOf(cityAlisbon)

        dataSource.getCitiesByName("Alisbón") shouldEqual Either.Success(expected)
    }

}