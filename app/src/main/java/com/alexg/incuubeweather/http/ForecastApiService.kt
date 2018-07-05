package com.alexg.incuubeweather.http


import com.alexg.incuubeweather.http.apimodel.Weather

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {

    @GET("yql?&format=json&")
    fun getTopRatedMovies(@Query(value = "q", encoded = true) query: String): Observable<Weather>

}
