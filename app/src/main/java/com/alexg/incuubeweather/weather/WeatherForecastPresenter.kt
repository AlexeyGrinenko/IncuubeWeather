package com.alexg.incuubeweather.weather


import com.alexg.incuubeweather.http.ApiModuleForForecast
import com.alexg.incuubeweather.http.ForecastApiService

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherForecastPresenter(private val service: ForecastApiService) : WeatherForecastActivityMVP.Presenter {

    private var view: WeatherForecastActivityMVP.View? = null
    private val subscription: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: WeatherForecastActivityMVP.View) {
        this.view = view
    }

    override fun detachView() {
        subscription.clear()
    }

    override fun loadData(location: String) {
        if (view != null) {
            view!!.setLoading(true)
        }

        subscription.add(
                service.getTopRatedMovies(ApiModuleForForecast.convertLocationToQuery(location))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate {
                            if (view != null) {
                                view!!.setLoading(false)
                            }
                        }
                        .subscribe({ topRated ->
                            if (view != null) {
                                view!!.updateData(topRated.query!!.results!!.channel!!)
                            }
                        }) { error ->
                            if (view != null) {
                                view!!.showSnackbar()
                                view!!.setLoading(false)
                            }
                        }
        )
    }
}