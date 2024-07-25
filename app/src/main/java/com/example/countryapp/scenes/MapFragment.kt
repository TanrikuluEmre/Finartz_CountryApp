// MapFragment.kt
package com.example.countryapp.scenes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.countryapp.R
import com.example.countryapp.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private var countryName: TextView? = null
    private var countryCapital: TextView? = null
    private var countryPopulation: TextView? = null
    private var countryFlag: ImageView? = null
    private var _binding: FragmentMapBinding? = null
    private var capitalCord1: Double? = null
    private var capitalCord2: Double? = null
    private var mapView: MapView? = null
    private lateinit var googleMap: GoogleMap
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
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryName = _binding!!.countryName
        countryCapital = _binding!!.countryCapital
        countryPopulation = _binding!!.countryPopulation
        countryFlag = _binding!!.countryFlag
        mapView = _binding!!.mapView

        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        val args = MapFragmentArgs.fromBundle(requireArguments())
        countryName?.text = args.countryName
        countryCapital?.text = args.countryCapital
        countryPopulation?.text = args.countryPopulation
        Glide.with(view.context).load(args.countryFlag).into(countryFlag!!)
        capitalCord1 = args.capitalCord1.toDouble()
        capitalCord2 = args.capitalCord2.toDouble()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val capitalLocation = LatLng(capitalCord1 ?: 0.0, capitalCord2 ?: 0.0)
        googleMap.addMarker(MarkerOptions().position(capitalLocation).title("Capital"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(capitalLocation, 10f))
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    companion object {
        fun newInstance() = MapFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}
