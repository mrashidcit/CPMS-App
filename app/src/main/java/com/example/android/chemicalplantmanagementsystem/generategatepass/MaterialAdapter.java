package com.example.android.chemicalplantmanagementsystem.generategatepass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Material;

import org.w3c.dom.Text;

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
    private HashMap<Integer, Material> mMaterialHashMap;

    public MaterialAdapter(@NonNull Context context, HashMap<Integer, Integer> data, ArrayList<Material> materialArrayList) {

        // Initialize Data Members
        mMaterialHashMap = new HashMap<Integer, Material>();


        mContext = context;
        mData = data;

        for (int i =0; i < materialArrayList.size(); i++) {
            Material material = materialArrayList.get(i);

            mMaterialHashMap.put(material.getId(), material);
        }


        mKeys = mData.keySet().toArray(new Integer[mData.size()]);

    }


    @Override
    public int getCount() {
        return mKeys.length;
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
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mKeys = mData.keySet().toArray(new Integer[mData.size()]);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.generategatepass_material_list_item, parent, false);
        }

        final Integer key = mKeys[position];
        Integer value = Integer.parseInt(getItem(position).toString());

        Material currentMaterial = mMaterialHashMap.get(key);

        TextView materialNameView = (TextView) convertView.findViewById(R.id.tv_material_name);
        TextView materialQtyView = (TextView) convertView.findViewById(R.id.tv_material_qty);
        Button deleteItemButtonView = (Button) convertView.findViewById(R.id.btn_delete_item);

        deleteItemButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(key);

                notifyDataSetChanged();
            }
        });

        materialNameView.setText(currentMaterial.getName());
        materialQtyView.setText(String.valueOf(value));

//        Log.v(LOG_TAG, "(key, qty) = (" + key + " , " +
//            value + " )"
//        );


        return convertView;
    }
}
