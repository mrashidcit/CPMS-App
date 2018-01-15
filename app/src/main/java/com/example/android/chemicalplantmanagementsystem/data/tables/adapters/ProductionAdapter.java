package com.example.android.chemicalplantmanagementsystem.data.tables.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Production;

import java.util.List;

/**
 * Created by Rashid Saleem on 15-Jan-18.
 */

public class ProductionAdapter extends ArrayAdapter<Production> {


    public ProductionAdapter(@NonNull Context context, @NonNull List<Production> productionList) {
        super(context, 0, productionList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.production_list_item, parent, false);

            // Find the Production item at given position in the list of productionList
            Production currentProductionItem = getItem(position);

            // Find the textView with view ID's
            TextView productionCodeView = (TextView) listItemView.findViewById(R.id.tv_production_code);
            TextView productionNameView = (TextView) listItemView.findViewById(R.id.tv_production_name);
            TextView productionStatusView = (TextView) listItemView.findViewById(R.id.tv_production_status);

            productionCodeView.setText(currentProductionItem.getProductionCode());
            productionNameView.setText(currentProductionItem.getProductionName());
            productionStatusView.setText(currentProductionItem.getProductionStatus() + "");
        }

        return listItemView;
    }
}











