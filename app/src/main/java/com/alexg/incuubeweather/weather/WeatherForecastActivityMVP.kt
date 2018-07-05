package com.alexg.incuubeweather.weather


import com.alexg.incuubeweather.http.apimodel.Channel

interface WeatherForecastActivityMVP {


    interface View {

        fun updateData(channel: Channel)

        fun showSnackbar()

        fun setLoading(isLoading: Boolean)

    }

    interface Presenter {

        fun loadData(location: String)

        fun detachView()

        fun attachView(view: WeatherForecastActivityMVP.View)

    }
}
