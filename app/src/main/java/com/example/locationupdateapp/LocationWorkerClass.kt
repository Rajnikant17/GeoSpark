package com.example.locationupdateapp

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.locationupdateapp.repository.LocationRepository


class LocationWorkerClass
@WorkerInject constructor(
        @Assisted val appContext: Context,
        @Assisted workerParams: WorkerParameters,
        val locationRepository: LocationRepository
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {

        return try {
            locationRepository.getLocation()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}
