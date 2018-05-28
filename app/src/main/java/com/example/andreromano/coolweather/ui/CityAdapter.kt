package com.example.andreromano.coolweather.ui

import android.databinding.generated.callback.OnClickListener
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andreromano.coolweather.City
import com.example.andreromano.coolweather.R
import com.example.andreromano.coolweather.databinding.ItemCityBinding
import kotlinx.android.synthetic.main.item_city.view.*


class CityAdapter(
    var cities: List<City>,
    val onClick: (City) -> Unit
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: City, listener: (City) -> Unit) {
            binding.item = item
            binding.listener = View.OnClickListener {
                listener(item)
            }
            // needed to correctly measure the view
            // https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
            binding.executePendingBindings()
        }
    }

    fun replaceData(items: List<City>) {
        cities = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position], onClick)
    }

    override fun getItemCount(): Int = cities.size

}