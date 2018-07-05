package com.alexg.incuubeweather.root

import com.alexg.incuubeweather.http.ApiModuleForForecast
import com.alexg.incuubeweather.weather.WeatherForecastActivity
import com.alexg.incuubeweather.weather.WeatherForecastModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModuleForForecast::class, WeatherForecastModule::class))
interface ApplicationComponent {

    fun inject(target: WeatherForecastActivity)

}
