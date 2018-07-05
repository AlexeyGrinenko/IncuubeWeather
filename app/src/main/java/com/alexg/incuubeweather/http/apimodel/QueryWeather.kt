package com.alexg.incuubeweather.http.apimodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QueryWeather {

    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("created")
    @Expose
    var created: String? = null
    @SerializedName("lang")
    @Expose
    var lang: String? = null
    @SerializedName("results")
    @Expose
    var results: Results? = null

}
