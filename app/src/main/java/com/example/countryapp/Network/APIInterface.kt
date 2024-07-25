package com.example.countryapp.network

import com.example.countryapp.network.response.Country
import com.example.countryapp.network.response.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("/v3.1/all")
    fun getCountries(): Call<List<Country>>

}