package com.alexg.incuubeweather.http.apimodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {

    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("region")
    @Expose
    var region: String? = null

}
