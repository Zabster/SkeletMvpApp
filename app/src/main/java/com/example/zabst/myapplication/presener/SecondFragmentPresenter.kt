package com.example.zabst.myapplication.presener

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpPresenter
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presenterinterface.SecondFragmentInterface
import com.google.android.gms.location.FusedLocationProviderClient

@InjectViewState
class SecondFragmentPresenter: MvpPresenter<SecondFragmentInterface>(), LocationListener{

    private val LOCATION_REFRESH_TIME: Long = 1000
    private val LOCATION_REFRESH_DISTANCE = 100.0f
    private var timeout: Int = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.getInstance()!!.getAppComponent()!!.inject(this)
    }

    fun updateLocation(context: Context, locationManager: LocationManager?) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, this)
        }
    }

    fun getLocations(fusedLocationProviderClient: FusedLocationProviderClient,
                     activity: MvpAppCompatActivity, context: Context,
                     locationManager: LocationManager?) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            updateLocation(context, locationManager)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener(activity) {
                Log.d("LastLocation", "it: $it")
                if (it?.longitude == null) {
                    updateLocation(context, locationManager)
                    timeout++
                    if (timeout > 15) {
                        viewState.progressButtonAndGetLocation(it, false)
                        timeout = 0
                        return@addOnSuccessListener
                    }
                    getLocations(fusedLocationProviderClient, activity, context, locationManager)
                } else {
                    viewState.progressButtonAndGetLocation(it, true)
                }
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("Location", "provider: $provider, status=$status, extras: $extras")
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onLocationChanged(location: Location?) {
        Log.d("Location", "location: ${location?.extras}")
        viewState.progressButtonAndGetLocation(location, false)
    }
}