package com.alexg.incuubeweather.weather


import com.alexg.incuubeweather.http.ForecastApiService

import dagger.Module
import dagger.Provides

@Module
class WeatherForecastModule {

    @Provides
    fun provideWeatherForecastActivityPresenter(forecastApiService: ForecastApiService): WeatherForecastActivityMVP.Presenter {
        return WeatherForecastPresenter(forecastApiService)
    }

}
