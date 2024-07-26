package com.example.countryapp.scenes

import com.example.countryapp.network.Network.service
import com.example.countryapp.network.response.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel_MainFragment {

    private var countries: List<Country>? = null

    public fun fetchAllCountries(myCallback: (result: List<Country>?) -> Unit){
        service.getCountries().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful && response.body() != null) {
                    countries = response.body()
                     myCallback.invoke(countries)
                } else {
                    myCallback.invoke(null)
                }
            }
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                myCallback.invoke(null)
            }
        })
    }
    public fun updateCountries(searchQuery: String?, lastCountries: List<Country>?,myCallback: (result: List<Country>?) -> Unit) {
        if(searchQuery!!.isNotEmpty()){
            val filteredList = lastCountries?.filter {
                it.name.common.contains(searchQuery, ignoreCase = true)
            }
            myCallback.invoke(filteredList)
        }
    }
}