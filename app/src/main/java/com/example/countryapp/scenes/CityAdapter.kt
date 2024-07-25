package com.example.countryapp.scenes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countryapp.R
import com.example.countryapp.databinding.FragmentCityAdapterBinding
import com.example.countryapp.network.response.Country

class CityAdapter(private val dataSet: List<Country>?) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    class ViewHolder(val binding: FragmentCityAdapterBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentCityAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.root.setOnClickListener{
            findNavController(holder.itemView).navigate(
                MainFragmentDirections.actionMainFragmentToMapFragment(
                    dataSet?.get(position)?.name?.common.toString(),
                    dataSet?.get(position)?.capital?.get(0).toString(),
                    dataSet?.get(position)?.population.toString(),
                    dataSet?.get(position)?.flags?.png.toString(),
                    dataSet?.get(position)?.capitalInfo?.latlng!![0].toString(),
                    dataSet?.get(position)?.capitalInfo?.latlng!![1].toString()
                )
            )
        }

        holder.binding.countryName.text = dataSet?.get(position)?.name?.common
        holder.binding.countryCapital.text = dataSet?.get(position)?.capital?.get(0).toString()
        holder.binding.countryPopulation.text = dataSet?.get(position)?.population.toString()
        Glide.with(holder.itemView.context).load(dataSet?.get(position)?.flags?.png).into(holder.binding.countryFlag)
    }
}