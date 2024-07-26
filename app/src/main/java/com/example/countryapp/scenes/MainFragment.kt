package com.example.countryapp.scenes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryapp.network.Network.service
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
    var mainFragmentVM : ViewModel_MainFragment = ViewModel_MainFragment()

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

        mainFragmentVM.fetchAllCountries(){ countries ->
            this.countries = countries
            setupAdapter(countries)
        }

        searchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                 return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                mainFragmentVM.updateCountries(p0,countries){ result ->
                    setupAdapter(result)
                    countries = result
                }
                return true
            }
        })


    }

    private fun setupAdapter(result : List<Country>?){
        val customerAdapter = CityAdapter(result)
        val recyclerView = _binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = customerAdapter
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