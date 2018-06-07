package com.example.andreromano.coolweather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.example.andreromano.coolweather.ui.DetailsFragment
import com.example.andreromano.coolweather.ui.ListFragment

class MainActivity : AppCompatActivity() {

    enum class Navigation {
        LIST,
        DETAILS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_content, ListFragment(), "ListFragment")
                .commitAllowingStateLoss()
        }
    }

    fun navigateTo(destination: Navigation, data: Bundle) {
        when (destination) {
            Navigation.DETAILS -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_content, DetailsFragment.newInstance(data), "DetailsFragment")
                    .commitAllowingStateLoss()
            }
        }
    }

}
