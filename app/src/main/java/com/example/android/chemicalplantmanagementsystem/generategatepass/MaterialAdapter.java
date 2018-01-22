package com.example.android.chemicalplantmanagementsystem.generategatepass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rashid Saleem on 22-Jan-18.
 */

public class MaterialAdapter extends BaseAdapter {

    private static final String LOG_TAG = MaterialAdapter.class.getSimpleName();

    private HashMap<Integer, Integer> mData;
    private Integer[] mKeys;
    private Context mContext;

    public MaterialAdapter(@NonNull Context context, HashMap<Integer, Integer> data) {

        mContext = context;
        mData = data;
        mKeys = mData.keySet().toArray(new Integer[mData.size()]);

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

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.generategatepass_material_list, parent, false);
        }

        Integer key = mKeys[position];
        Integer value = Integer.parseInt(getItem(position).toString());

        Log.v(LOG_TAG, "(key, qty) = (" + key + " , " +
            value + " )"
        );


        return convertView;
    }
}
