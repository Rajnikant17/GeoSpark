package com.example.locationupdateapp.ui

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.geosparklocation.R
import com.example.geosparklocation.databinding.ActivityMainBinding
import com.example.locationupdateapp.model.Location
import com.example.locationupdateapp.model.Response
import com.example.locationupdateapp.viewmodel.MainActivityViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var activityMainBinding:ActivityMainBinding? = null
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    lateinit var tv_start:Button
    lateinit var tv_end:Button
    var statTime:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(activityMainBinding?.root)
        tv_start=findViewById(R.id.tv_start)
        tv_end=findViewById(R.id.tv_end)
        checkGpsEnabledOrNot()
        observeLocationPermissionStatus()

        tv_start.setOnClickListener {
            mainActivityViewModel.startWorkManager()
            // get the start time
            statTime=SimpleDateFormat("dd-MMM-yyyy hh:mm ss a", Locale.getDefault()).format(Date())
        }

        tv_end.setOnClickListener {
            mainActivityViewModel.StopFetchingLocation()
            val gson = Gson()
            val json: String? = sharedPreferences.getString("locationlist", null)
            val type: Type = object : TypeToken<ArrayList<Location?>?>() {}.type
            val locationList:List<Location> = gson.fromJson(json, type) as ArrayList<Location>
            locationList.forEach {
                Log.d("sfdkjsefsjef", "Time span" + it.timestamp.toString())
                Log.d("sfdkjsefsjef", "Latitude" + it.latitude.toString())
                Log.d("sfdkjsefsjef", "Longitude" + it.longitide.toString())
                Log.d("sfdkjsefsjef", "Accuracy" + it.accuracy.toString())
            }

            val response:Response= Response()
            response.startTime=statTime
            response.endTime=SimpleDateFormat("dd-MMM-yyyy hh:mm ss a", Locale.getDefault()).format(Date())
            response.locations=locationList
            response.tripId= 1.toString()

        }
    }

    fun checkGpsEnabledOrNot() {
        if (mainActivityViewModel.isGPSEnabled()) {
            mainActivityViewModel.checkLocationPermission()
        } else {
            Toast.makeText(this, "Please turn on the GPS and restart the App.", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun observeLocationPermissionStatus() {
        mainActivityViewModel.LiveDataLocationPermission.observe(this, Observer {
            if (!it) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), 2000
                )
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2000 -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted.
                    Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, getString(R.string.permission_string), Toast.LENGTH_LONG)
                        .show()
                    ActivityCompat.finishAffinity(this)
                }
                return
            }
        }
    }

}