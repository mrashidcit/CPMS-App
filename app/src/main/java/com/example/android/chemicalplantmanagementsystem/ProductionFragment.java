package com.example.android.chemicalplantmanagementsystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.chemicalplantmanagementsystem.data.tables.Production;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.ProductionAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link ProductionFragment} factory method to
 * create an instance of this fragment.
 */
public class ProductionFragment extends Fragment {

    private ProductionAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View productionView = inflater.inflate(R.layout.fragment_production, container, false);

        ListView productionListView = (ListView) productionView.findViewById(R.id.list_production);

        mAdapter = new ProductionAdapter(getContext(), new ArrayList<Production>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        productionListView.setAdapter(mAdapter);

        List<Production> productionList = new ArrayList<Production>();
        productionList.add(new Production("oli38783", "The name", 1));
        productionList.add(new Production("oli38783", "The name", 1));
        productionList.add(new Production("oli38783", "The name", 1));
        productionList.add(new Production("oli38783", "The name", 1));
        productionList.add(new Production("oli38783", "The name", 1));
        productionList.add(new Production("oli38783", "The name", 1));
        productionList.add(new Production("oli38783", "The name", 1));

        mAdapter.clear();
        mAdapter.addAll(productionList);




        return productionView;
    }



}
