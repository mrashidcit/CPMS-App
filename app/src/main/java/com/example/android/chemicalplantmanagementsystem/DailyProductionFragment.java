package com.example.android.chemicalplantmanagementsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.Production;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyProductionFragment} factory method to
 * create an instance of this fragment.
 */
public class DailyProductionFragment extends Fragment{

    private AdapterView.OnItemSelectedListener spinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.v("MainActivity", parent.getItemAtPosition(position) + "");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private List<Product> mProductList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_daily_production, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.planets_array, android.R.layout.simple_spinner_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(spinnerItemSelectedListener);

//        ArrayAdapter<Product> adapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_spinner_dropdown_item ,mProductList);




        mProductList = new ArrayList<Product>();

        mProductList.add(new Product("ad333", "Mineral Water", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product("ad333", "Fast Food", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product("ad333", "Fresh Air", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product("ad333", "Nothing", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product("ad333", "Something", 0, "Pure May be Fresh Water"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);

        for (int i = 0; i < mProductList.size(); i++) {
            adapter.add(mProductList.get(i).getName());
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerItemSelectedListener);



        return view;
    }



}
