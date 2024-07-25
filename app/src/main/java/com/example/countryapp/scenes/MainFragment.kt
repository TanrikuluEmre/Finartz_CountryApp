package com.example.countryapp.scenes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryapp.Network.Network
import com.example.countryapp.Network.Network.service
import com.example.countryapp.databinding.FragmentMainBinding
import com.example.countryapp.network.response.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    private var searchBar:SearchView? = null
    private var countries: List<Country>? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBar = _binding!!.searchBar
        fetchAllCountries()
        searchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                 return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                updateCountries(p0.toString())
                return true
            }
        })


    }
    private fun updateCountries(searchQuery: String) {
        if(searchQuery.isNotEmpty()){
            val filteredList = countries?.filter {
                it.name.common.contains(searchQuery, ignoreCase = true)
            }
            setupAdapter(filteredList)
        }
    }
    private fun setupAdapter(result : List<Country>?){
        val customerAdapter = CityAdapter(result)
        val recyclerView = _binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = customerAdapter
    }

    private fun fetchAllCountries(){
        service.getCountries().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful && response.body() != null) {
                    countries = response.body()
                    setupAdapter(countries)
                    Log.d(tag,"Success")
                } else {
                    Log.e(tag, "Error onResponse")
                }
            }
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.e(tag, "Error onFailure")
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}