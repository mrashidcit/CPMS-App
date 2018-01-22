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
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;

import java.util.List;

/**
 * Created by Rashid Saleem on 13-Jan-18.
 */

public class GatePassAdapter extends ArrayAdapter<GatePass> {

//public class GatePassAdapter extends <GatePass> {

    public GatePassAdapter(@NonNull Context context, List<GatePass> gatePasses) {
        super(context, 0, gatePasses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.gate_pass_list_item, parent, false);

            // Find the GatePass at the given position in the list of gatePasses
            GatePass currentGatePass = getItem(position);

            // Find the TextView with view ID tv_person_name
            TextView personNameView = (TextView) listItemView.findViewById(R.id.tv_person_name);
            TextView itemNameView = (TextView) listItemView.findViewById(R.id.tv_item_name);
//            TextView quantityView = (TextView) listItemView.findViewById(R.id.tv_quantity);
            TextView destinationView = (TextView) listItemView.findViewById(R.id.tv_destination);

            // Display Info in views
            personNameView.setText(currentGatePass.getPersonName());
            itemNameView.setText(currentGatePass.getItemName());
//            quantityView.setText(currentGatePass.getQuantity() + "");
            destinationView.setText(currentGatePass.getDestination());

        }

        return listItemView;
    }
}















