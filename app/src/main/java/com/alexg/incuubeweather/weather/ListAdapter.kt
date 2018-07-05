package com.alexg.incuubeweather.weather

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexg.incuubeweather.R
import com.alexg.incuubeweather.helper.MyDiffUtil
import com.alexg.incuubeweather.http.apimodel.Forecast
import java.util.*

class ListAdapter(private val mForecastList: MutableList<Forecast>) : RecyclerView.Adapter<ListAdapter.ListItemViewHolder>() {

    private var mTemperatureUnits = "F"
    private var mContext: Context? = null

    private val data: List<Forecast>
        get() = mForecastList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_forecast, parent, false)
        return ListItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.forecastDate.text = mForecastList[position].day + ", " + mForecastList[position].date
        holder.forecastMax.text = (mForecastList[position].high
                + " " + mContext!!.getString(R.string.text_symbol_degree) + mTemperatureUnits)
        holder.forecastMin.text = (mForecastList[position].low
                + "  " + mContext!!.getString(R.string.text_symbol_degree) + mTemperatureUnits)
        holder.forecastText.text = mForecastList[position].text
    }

    override fun getItemCount(): Int {
        return mForecastList.size
    }

    fun updateData(newList: List<Forecast>, tempSymbol: String) {
        mTemperatureUnits = tempSymbol
        val oldList = ArrayList(mForecastList)
        val diffUtil = MyDiffUtil(mForecastList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        mForecastList.clear()
        mForecastList.addAll(oldList)
        mForecastList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var forecastText: TextView = itemView.findViewById(R.id.item_forecast_text)
        var forecastDate: TextView = itemView.findViewById(R.id.item_forecast_date)
        var forecastMax: TextView = itemView.findViewById(R.id.item_forecast_max)
        var forecastMin: TextView = itemView.findViewById(R.id.item_forecast_min)

    }
}