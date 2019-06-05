package com.brustoloni.weather.presentation.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.brustoloni.weather.utils.Constants
import com.brustoloni.weather.utils.Constants.Companion.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


val WEATHER_FRAGMENT_TAG: String = WeatherFragment::class.java.name

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModel()

    private lateinit var dataBinding: com.brustoloni.weather.databinding.FragmentWeatherBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var flagPermission = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil
            .inflate(inflater, com.brustoloni.weather.R.layout.fragment_weather, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            super.onSaveInstanceState(savedInstanceState)
        }
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.executePendingBindings()

        requestPermission()

        initializeRecyclerView()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && !flagPermission) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            callApi(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
        }
    }

    private fun callApi(lat: Double, lon: Double) {
        if (lat != 0.0 && lon != 0.0 && !viewModel.flagFirstLoad.value!!) {
            viewModel.flagFirstLoad.value = true
            viewModel.start("$lat,$lon")
        } else if (!viewModel.flagFirstLoad.value!!) {
            Log.e(WEATHER_FRAGMENT_TAG, "HARDCODE GEOLOCATION")
            viewModel.flagFirstLoad.value = true
            viewModel.start(Constants.FAKE_LOCATION)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION-> {
                flagPermission = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                if (flagPermission) {
                    requestPermission()
                } else {
                        val s = Snackbar.make(dataBinding.rvWeather,
                            "We need your location permission to continue or allow it in Setting.",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction("Request Again") {requestPermission() }
                        val textView = s.view.findViewById<View>(com.brustoloni.weather.R.id.snackbar_text) as TextView
                        textView.maxLines = 3
                        s.show()

                }
                return
            }
        }
    }

    private fun initializeRecyclerView() {
        val adapter = WeatherAdapter()
        dataBinding.rvWeather.adapter = adapter
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val type = viewModel.dataReceived.value?.get(position)?.type

                return if ( type != 0 && type != 2 && type != 3) { 1 } else { 2 }
            }
        }

        dataBinding.rvWeather.layoutManager = gridLayoutManager
        viewModel.dataReceived.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
    }
}
