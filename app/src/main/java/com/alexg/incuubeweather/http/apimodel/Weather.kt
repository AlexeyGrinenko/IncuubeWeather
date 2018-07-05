package com.alexg.incuubeweather.http.apimodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Weather {

    @SerializedName("query")
    @Expose
    var query: QueryWeather? = null

}
