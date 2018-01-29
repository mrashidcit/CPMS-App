package com.example.android.chemicalplantmanagementsystem;

import android.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.GatePassAdapter;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class GatePassFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks {

    private static final int GATEPASS_LOADER_ID = 1001;

    private static String GATEPASS_URL = "";

    private Context mContext;


    private final static String LOG_TAG = GatePassFragment.class.getSimpleName();

    // Adapter for the list of GatePasses
    private GatePassAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        GATEPASS_URL = "http://" + mContext.getString(R.string.host_address) + "/api/GatePass";
//        GATEPASS_URL = "http://" + mContext.getString(R.string.host_address) + "/api/user";


        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.v(LOG_TAG, "url = " + GATEPASS_URL);

        // Inflate the layout for this fragment
        View gatePassView = inflater.inflate(R.layout.fragment_gate_pass, container, false);

        // Find a reference to the {@link Listview} in the layout
        ListView gatePassListView = (ListView) gatePassView.findViewById(R.id.list_gate_pass);

        mAdapter = new GatePassAdapter(getContext(), new ArrayList<GatePass>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        gatePassListView.setAdapter(mAdapter);

         android.support.v4.app.LoaderManager loaderManager = getLoaderManager();

         loaderManager.initLoader(GATEPASS_LOADER_ID, null, this);



//        ArrayList<GatePass> gatePasses = new ArrayList<GatePass>();
//        gatePasses.add(new GatePass("Ali", "Sooda", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Usman", "Pestiside", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Bilal", "Tea", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Waqas", "Water", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Jemmi", "Blank", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Baqar", "Mineral", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Zahid", "Meal", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Abubakar", "Beef", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Shabeer", "Sooda", 8, "Lahore", "03328738738", "Good"));
//        gatePasses.add(new GatePass("Ali", "Cups", 8, "Lahore", "03328738738", "Good"));
//
//
//        mAdapter.clear();
//        mAdapter.addAll(gatePasses);

//        User user = new User(getContext());
//
//        Log.v(LOG_TAG, "token = " + user.getToken());

        return gatePassView;
    }


    @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
        // Create a new loader for the give URL

        return new GatePassLoader(mContext, GATEPASS_URL);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {

        Log.v(LOG_TAG, "data = " + data.toString()  );

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

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



















