package com.example.android.chemicalplantmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.GatePassAdapter;

import java.util.ArrayList;

public class GatePassActivity extends AppCompatActivity {

    private final static String LOG_TAG = GatePassActivity.class.getSimpleName();

    // Adapter for the list of GatePasses
    private GatePassAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_pass_list);

        // Find a reference to the {@link ListView} in the layout
        ListView gatePassListView = (ListView) findViewById(R.id.list_gate_pass);

        mAdapter = new GatePassAdapter(this, new ArrayList<GatePass>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        gatePassListView.setAdapter(mAdapter);



        ArrayList<GatePass> gatePasses = new ArrayList<GatePass>();
        gatePasses.add(new GatePass("Ali", "Sooda",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Usman", "Pestiside",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Bilal", "Tea",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Waqas", "Water",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Jemmi", "Blank",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Baqar", "Mineral",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Zahid", "Meal",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Abubakar", "Beef",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Shabeer", "Sooda",8,"Lahore","03328738738", "Good"));
        gatePasses.add(new GatePass("Ali", "Cups",8,"Lahore","03328738738", "Good"));


        mAdapter.clear();
        mAdapter.addAll(gatePasses);




    }
}
