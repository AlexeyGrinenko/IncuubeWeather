package com.alexg.incuubeweather.helper;

import android.support.v7.util.DiffUtil;

import com.alexg.incuubeweather.http.apimodel.Forecast;

import java.util.List;

public class MyDiffUtil extends DiffUtil.Callback {

    private List<Forecast> oldList;
    private List<Forecast> newList;

    public MyDiffUtil(List<Forecast> oldList, List<Forecast> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getDate() == newList.get(newItemPosition).getDate();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

}