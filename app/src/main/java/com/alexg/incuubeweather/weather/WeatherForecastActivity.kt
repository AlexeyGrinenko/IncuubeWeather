package com.alexg.incuubeweather.weather

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.alexg.incuubeweather.R
import com.alexg.incuubeweather.Utils.InternetUtils
import com.alexg.incuubeweather.helper.DividerItemDecoration
import com.alexg.incuubeweather.http.apimodel.Channel
import com.alexg.incuubeweather.http.apimodel.Forecast
import com.alexg.incuubeweather.root.App
import kotlinx.android.synthetic.main.weather_forecast_activity.*
import java.util.*
import javax.inject.Inject

class WeatherForecastActivity : AppCompatActivity(), WeatherForecastActivityMVP.View {

    private val TAG = WeatherForecastActivity::class.java.name

    var presenter: WeatherForecastActivityMVP.Presenter? = null
        @Inject set

    private var listAdapter: ListAdapter? = null
    private val resultList = ArrayList<Forecast>()
    private var locationForWeather = "kiev"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_forecast_activity)

        (application as App).component!!.inject(this)

        setSupportActionBar(findViewById(R.id.weather_activity_toolbar))

        initRecyclerView()

        if (InternetUtils.isNetworkAvailable(this)) {
            presenter!!.loadData(locationForWeather)
        } else {
            showInternetErrorDialog()
        }
    }

    private fun showInternetErrorDialog() {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setMessage(getString(R.string.text_error_internet))

        alertDialog.setPositiveButton(
                resources.getString(R.string.text_ok)
        ) { dialog, _ -> dialog.dismiss() }

        alertDialog.show()
    }

    private fun initRecyclerView() {
        swipeRefreshLayout!!.setOnRefreshListener { presenter!!.loadData(locationForWeather) }

        listAdapter = ListAdapter(resultList)
        recyclerView!!.adapter = listAdapter
        recyclerView!!.addItemDecoration(DividerItemDecoration(this))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        presenter!!.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        initSearchView(menu.findItem(R.id.action_search).actionView as SearchView)
        return true
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.requestFocus()
        searchView.queryHint = getString(R.string.text_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                locationForWeather = query
                searchView.clearFocus()
                presenter!!.loadData(locationForWeather)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.detachView()
    }

    override fun updateData(channel: Channel) {
        listAdapter!!.updateData(channel.item!!.forecast!!, channel.units!!.temperature!!)

        locationNameTV!!.text = channel.location!!.city + ", " + channel.location!!.country + ", " + channel.location!!.region
        currentDateTV!!.text = channel.item!!.condition!!.date
        currentConditionTV!!.text = channel.item!!.condition!!.text
        currentTemperatureTV!!.text = (channel.item!!.condition!!.temp + getString(R.string.text_symbol_degree)
                + " " + channel.units!!.temperature)
    }

    override fun showSnackbar() {
        Snackbar.make(rootView!!, getString(R.string.text_error_location), Snackbar.LENGTH_LONG).show()
    }

    override fun setLoading(isLoading: Boolean) {
        swipeRefreshLayout!!.isRefreshing = isLoading
    }
}