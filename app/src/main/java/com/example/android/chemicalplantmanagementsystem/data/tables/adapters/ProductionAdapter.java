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
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract.ProductionEntry;

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
            TextView productionNameView = (TextView) listItemView.findViewById(R.id.tv_name);
            TextView productionStatusView = (TextView) listItemView.findViewById(R.id.tv_status);
            TextView productionDescriptionView = (TextView) listItemView.findViewById(R.id.tv_description);


            productionNameView.setText(currentProductionItem.getProductionName());
            productionDescriptionView.setText(currentProductionItem.getDescription());

            int status = currentProductionItem.getProductionStatus();
            if (status == ProductionEntry.PENDING_STATUS){
                productionStatusView.setText("Pending");
            } else if (status == ProductionEntry.APPROVED_STATUS){
                productionStatusView.setText("Approved");
            }else if (status == ProductionEntry.COMPLETED_STATUS){
                productionStatusView.setText("completed");
            } else {
                productionStatusView.setText("Unknown");
            }

        }

        return listItemView;
    }
}

