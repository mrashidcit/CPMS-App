package com.example.android.chemicalplantmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.android.chemicalplantmanagementsystem.data.tables.Production;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.ProductionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductionActivity extends AppCompatActivity {

    private static final String LOG_TAG = ProductionActivity.class.getSimpleName();
    private ProductionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_list);


        ListView productionListView = (ListView) findViewById(R.id.list_production);

        mAdapter = new ProductionAdapter(this, new ArrayList<Production>());

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

//        for (int i = 0; i < productionList.size(); i++) {
//            Log.v(LOG_TAG, productionList.get(i).getProductionCode() + " , "
//                    + productionList.get(i).getProductionName() + " , "
//                    + productionList.get(i).getProductionStatus()
//            );

        mAdapter.clear();
        mAdapter.addAll(productionList);




     }


}
