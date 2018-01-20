package com.example.android.chemicalplantmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.GatePassAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class GatePassFragment extends Fragment {

    private final static String LOG_TAG = GatePassFragment.class.getSimpleName();

    // Adapter for the list of GatePasses
    private GatePassAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gatePassView = inflater.inflate(R.layout.fragment_gate_pass, container, false);

        // Find a reference to the {@link Listview} in the layout
        ListView gatePassListView = (ListView) gatePassView.findViewById(R.id.list_gate_pass);

        mAdapter = new GatePassAdapter(getContext(), new ArrayList<GatePass>());

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

        return gatePassView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_new) {

            Intent intent = new Intent(getContext(), GenerateGatePassActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



















