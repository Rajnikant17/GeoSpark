package com.example.locationupdateapp.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Looper
import android.util.Log
import com.example.locationupdateapp.di.impl1
import com.example.locationupdateapp.model.Location
import com.google.android.gms.location.*
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList


@Singleton
class LocationRepository
@Inject
constructor(
    @impl1 val locationRequestWhenTimeChanges: LocationRequest,
    val pref: SharedPreferences,
    val fusedLocationClient: FusedLocationProviderClient
) {

    var locationCallback: LocationCallback?=null

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val editor=pref.edit()
        val locationList=ArrayList<Location>()
        val gson = Gson()

         locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with local database
                    val mylocation =Location()
                    mylocation.latitude=location.latitude
                    mylocation.longitide=location.longitude
                    mylocation.accuracy=location.accuracy
                    mylocation.timestamp= location.time.toString()
                    locationList.add(mylocation)
                    val gsonString = gson.toJson(locationList)
                    editor.putString("locationlist",gsonString)
                    editor.apply()
                    Log.d("sdfcsfhegf", location.longitude.toString())
                }
            }
        }
        startLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationCallback: LocationCallback?) {
        // we have created two request becuase both the condition i.e 200 meter and 30 sec were not getting achieved independently from one request.

        //this request will trigger when time changes to 30 sec
        fusedLocationClient.requestLocationUpdates(
            locationRequestWhenTimeChanges,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLoactionUpdate()
    {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}