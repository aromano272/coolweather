package com.example.andreromano.coolweather.ui

import com.example.andreromano.coolweather.City
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    private val city = City(8013113, "Almada", "PT")

    @Before
    fun setup() {
        viewModel = DetailsViewModel(
            city,
        )
    }

}