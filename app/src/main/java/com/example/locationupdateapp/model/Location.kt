package com.example.locationupdateapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Location (
    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null,

    @SerializedName("longitide")
    @Expose
    var longitide: Double? = null,

    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null,

    @SerializedName("accuracy")
    @Expose
    var accuracy: Float? = null
)