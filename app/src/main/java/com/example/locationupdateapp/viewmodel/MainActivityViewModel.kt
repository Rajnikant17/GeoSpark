package com.example.locationupdateapp.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.locationupdateapp.LocationWorkerClass
import com.example.locationupdateapp.repository.LocationRepository


class MainActivityViewModel
@ViewModelInject
constructor(application: Application,val locationRepository: LocationRepository) : ViewModel() {
    val globalContext = application
    var LiveDataLocationPermission: MutableLiveData<Boolean> = MutableLiveData()
    fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                globalContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                globalContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            LiveDataLocationPermission.value = false
            return
        } else {
            LiveDataLocationPermission.value = true
        }
    }

    fun isGPSEnabled() =
        (globalContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

    fun startWorkManager() {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<LocationWorkerClass>().build()
        WorkManager.getInstance(globalContext).enqueue(
            uploadWorkRequest
        )
    }

    fun StopFetchingLocation()
    {
        locationRepository.stopLoactionUpdate()
    }
}