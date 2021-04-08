package com.example.locationupdateapp.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
 data class Response (
     @SerializedName("trip_id")
     @Expose
     var tripId: String? = null,

     @SerializedName("start_time")
     @Expose
     var startTime: String? = null,

     @SerializedName("end_time")
     @Expose
     var endTime: String? = null,

     @SerializedName("locations")
     @Expose
     var locations: List<Location?>? = null
)