package com.example.android.chemicalplantmanagementsystem.generategatepass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.android.chemicalplantmanagementsystem.data.tables.Material;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rashid Saleem on 22-Jan-18.
 */

public class MaterialAdapter extends BaseAdapter {

    private static final String LOG_TAG = MaterialAdapter.class.getSimpleName();
    private HashMap<Integer, Integer> mData = new HashMap<Integer, Integer>();
    private Integer[] mKeys;

    public void HashMapAdapter(HashMap<Integer, Integer> data) {
        mData = data;
        mKeys = mData.keySet().toArray(new Integer[data.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int key = mKeys[position];
        int qty = Integer.parseInt(getItem(position).toString());

        Log.v(LOG_TAG, "(key, qty) = (" + key + " , " +
            qty + " )"
        );


        return convertView;
    }
}
