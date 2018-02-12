package com.example.android.chemicalplantmanagementsystem.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.DailyProduction;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyProductionFragment} factory method to
 * create an instance of this fragment.
 */
public class DailyProductionFragment extends Fragment{

    private static final String LOG_TAG = "MainActivity";

    private Button mSubmitView;
    private View mSelectProductContainer;

    private TextView  mProducedView;
    private TextView  mDispatchesView;
    private TextView  mSaleReturnView;
    private TextView  mReceivedView;
    private Button mSaveButtonView;
    private View mDailyProductionFormContainer;

    private List<Product> mProductList;

    private int mCurrentProductPosition;

    private Product mCurrentProduct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_daily_production, container, false);

        // Getting Control Views Objects
        mProducedView = (TextView) view.findViewById(R.id.et_produced);
        mDispatchesView = (TextView) view.findViewById(R.id.et_dispatches);
        mSaleReturnView = (TextView) view.findViewById(R.id.et_sale_return);
        mReceivedView = (TextView) view.findViewById(R.id.et_received);
        mSaveButtonView = (Button) view.findViewById(R.id.btn_save);

        mSaveButtonView.setOnClickListener(mSaveClickListener);

        // Containers
        mSelectProductContainer = view.findViewById(R.id.container_select_product);
        mDailyProductionFormContainer = view.findViewById(R.id.container_daily_production_form);


        Spinner spinner = (Spinner) view.findViewById(R.id.planets_spinner);

        mSubmitView = (Button) view.findViewById(R.id.btn_submit);

        mSubmitView.setOnClickListener(mSubmitClickListener);

        mProductList = new ArrayList<Product>();

        mProductList.add(new Product(12, "ad333", "Mineral Water", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(13, "ad333", "Fast Food", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(14, "ad333", "Fresh Air", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(15, "ad333", "Nothing", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(16, "ad333", "Something", 0, "Pure May be Fresh Water"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);

        for (int i = 0; i < mProductList.size(); i++) {
            adapter.add(mProductList.get(i).getName());
        }
        spinner.setAdapter(adapter);
        mCurrentProductPosition = 0;
        spinner.setOnItemSelectedListener(spinnerItemSelectedListener);

        return view;

    }

    // Click Listeners
    private AdapterView.OnItemSelectedListener spinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Log.v("MainActivity", parent.getItemAtPosition(position) + "" +
//                                        "id = " + id );

            // Storing current Position of the product for temporary use
            mCurrentProductPosition = position;
            Product currentProduct = mProductList.get(position);


            Log.v("MainActivity", "(id, name)" + "(" +
                    currentProduct.getId() + " , " +
                    currentProduct.getName()  + "d )"
            );

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnClickListener mSubmitClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mCurrentProduct = mProductList.get(mCurrentProductPosition);

            // Hiding container_select_product
            mSelectProductContainer.setVisibility(View.GONE);

            // Showing container_daily_production_form
            mDailyProductionFormContainer.setVisibility(View.VISIBLE);

            Log.v(LOG_TAG, "(id, name )" + "( " +
                    mCurrentProduct.getId() + " , " +
                    mCurrentProduct.getName() + " ) "
            );

        }
    };

    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int productId = (int) mCurrentProduct.getId();
            int produced = Integer.parseInt(mProducedView.getText().toString());
            int dispatches = Integer.parseInt(mDispatchesView.getText().toString());
            int saleReturn = Integer.parseInt(mSaleReturnView.getText().toString());
            int received = Integer.parseInt(mReceivedView.getText().toString());

            DailyProduction dailyProduction = new DailyProduction(productId, produced, dispatches, saleReturn, received);

            Log.v(LOG_TAG, "(id, produced, dispatches, saleReturn, received) = ( " +
                    dailyProduction.getProductId() + " , " +
                    dailyProduction.getProduced() + " , " +
                    dailyProduction.getDispatches() + " , " +
                    dailyProduction.getSaleReturn() + " , " +
                    dailyProduction.getReceived() + " ) "
            );


        }
    };



}
