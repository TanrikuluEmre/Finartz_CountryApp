package com.example.countryapp.network.response

data class SearchResultResponse(val results:List<Country>)
data class Country (val name: Name,
                    val capital: List<String>?,
                    val population: Int,
                    val flags: Flag,
                    val capitalInfo: CapitalInfo)

data class CapitalInfo (val latlng: List<Double>)
data class Name (val common: String)
data class Flag (val png: String)