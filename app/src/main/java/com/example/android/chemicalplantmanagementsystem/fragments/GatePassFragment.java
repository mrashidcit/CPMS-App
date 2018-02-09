package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.GenerateGatePassActivity;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.GatePassAdapter;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.GatePassContract.GatePassEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassEditorLoader;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class GatePassFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks {

    private final static String LOG_TAG = GatePassFragment.class.getSimpleName();
    private static final int GATEPASS_LOADER_ID = 1001;

    // use to which type of request we are making
    private int mRequestCode;

    private ListView mGatePassListView;
    private ProgressBar mLoadingIndicator;

    private HashMap<Integer, GatePass> mGatePassHashMap;
    private ArrayList<GatePass> mGatePassArrayList;

    private Context mContext;

    // Adapter for the list of GatePasses
    private GatePassAdapter mGatePassAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();


        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View gatePassView = inflater.inflate(R.layout.fragment_gate_pass, container, false);

        // Find a reference to the {@link Listview} in the layout
        mGatePassListView = (ListView) gatePassView.findViewById(R.id.list_gate_pass);
        mLoadingIndicator = (ProgressBar) gatePassView.findViewById(R.id.pb_loading_indicator);

        // Initialize Data Members
        mGatePassAdapter = new GatePassAdapter(getContext(), new ArrayList<GatePass>());
        mGatePassHashMap = new HashMap<Integer, GatePass>();
        mGatePassArrayList = new ArrayList<GatePass>();

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        mGatePassListView.setAdapter(mGatePassAdapter);
        mGatePassListView.setOnItemClickListener(mGatePassListItemClickListener);

        // Generate and show Fake Data
//        generateFakeGatePasses();



        String jsonResponseString = "[{\"id\":1,\"person_name\":\"altaf driver\",\"contact_phone\":\"12345678901\",\"destination\":\"department1store\",\"remarks\":\"default remarks\",\"created_at\":\"2018-01-19 14:44:34\",\"updated_at\":\"2018-01-30 22:41:28\"},{\"id\":2,\"person_name\":\"usman\",\"contact_phone\":\"08738489387\",\"destination\":\"Lahore\",\"remarks\":\"Hello to the furtue\",\"created_at\":\"2018-01-29 21:54:16\",\"updated_at\":\"2018-01-29 21:54:16\"},{\"id\":18,\"person_name\":\"Ali Abrar\",\"contact_phone\":\"12345678901\",\"destination\":\"Lahore\",\"remarks\":\"sincere\",\"created_at\":\"2018-02-09 11:25:59\",\"updated_at\":\"2018-02-09 11:25:59\"},{\"id\":19,\"person_name\":\"Ali Abrar\",\"contact_phone\":\"12345678901\",\"destination\":\"Lahore\",\"remarks\":\"sincere\",\"created_at\":\"2018-02-09 11:30:55\",\"updated_at\":\"2018-02-09 11:30:55\"},{\"id\":20,\"person_name\":\"Ali Abrar\",\"contact_phone\":\"12345678901\",\"destination\":\"Lahore\",\"remarks\":\"sincere\",\"created_at\":\"2018-02-09 11:36:21\",\"updated_at\":\"2018-02-09 11:36:21\"},{\"id\":21,\"person_name\":\"Ali Abrar\",\"contact_phone\":\"12345678901\",\"destination\":\"Lahore\",\"remarks\":\"sincere\",\"created_at\":\"2018-02-09 11:39:29\",\"updated_at\":\"2018-02-09 11:39:29\"}]";


//        Log.v(LOG_TAG, "jsonResponseString: " + jsonResponseString);

        JSONArray jsonArray = null;
        JSONObject gatePassJson = null;
        try {
            jsonArray = new JSONArray(jsonResponseString);


            for (int i =0; i < jsonArray.length(); i++) {
                gatePassJson = jsonArray.getJSONObject(i);

                int id = gatePassJson.getInt(GatePassEntry._ID);
                String personName = gatePassJson.getString(GatePassEntry.COLUMN_PERSON_NAME);
                String contactPhone = gatePassJson.getString(GatePassEntry.COLUMN_CONTACT_PHONE);
                String remarks = gatePassJson.getString(GatePassEntry.COLUMN_REMARKS);
                String destination = gatePassJson.getString(GatePassEntry.COLUMN_DESTINATION);

                mGatePassHashMap.put(id, new GatePass(id, personName, contactPhone, destination, remarks));

//                Log.v(LOG_TAG, "( " + id + " , " +
//                        personName + " , " +
//                        contactPhone + " , " +
//                        remarks + " , " +
//                        destination + " )"
//                );

            }

//            Log.v(LOG_TAG, "HashMapSize: " + mGatePassHashMap.size());

            Integer [] mKeys = mGatePassHashMap.keySet().toArray(new Integer[mGatePassHashMap.size()]);
            for (int i = 0; i < mGatePassHashMap.size(); i++) {

                mGatePassArrayList.add(mGatePassHashMap.get(mKeys[i]));
            }


        mLoadingIndicator.setVisibility(View.GONE);
        mGatePassAdapter.addAll(mGatePassArrayList);
        mGatePassAdapter.notifyDataSetChanged();
        mGatePassListView.performItemClick(mGatePassListView.getChildAt(0), 0, 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }


//        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//            android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
//
//            // we are maing GET request
//            mRequestCode = Api.CODE_GET_REQUEST;
//            loaderManager.initLoader(GATEPASS_LOADER_ID, new Bundle(), this);
//
//        } else {
//
//            Toast.makeText(mContext, "Unable to Connect!" , Toast.LENGTH_LONG).show();
//
//        }

        return gatePassView;
    }


    @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {

        Log.v(LOG_TAG, "onCreateLoader()");

        mLoadingIndicator.setVisibility(View.VISIBLE); // Show ProgressBar

        if (Api.CODE_GET_REQUEST == mRequestCode) {

            args.putInt(Api.REQUEST_CODE, mRequestCode);
            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));

            return new GatePassEditorLoader(mContext, Api.GATE_PASS_URL, args);

        }
        return  null;

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {

        Log.v(LOG_TAG, "onLoadFinished()");

//        if ( data == null) {
//            return;
//        }
        // otherwise hide Loading Indciator
//        mLoadingIndicator.setVisibility(View.GONE);
//
//        HashMap<Integer, GatePass> gatePassHashMap = (HashMap<Integer, GatePass>) data;
//
//        Integer mKeys[] = gatePassHashMap.keySet().toArray(new Integer[gatePassHashMap.size()]);
//        ArrayList<GatePass> gatePassArrayList = new ArrayList<GatePass>();
//        for (int i = 0; i < mKeys.length; i++) {
//            gatePassArrayList.add(gatePassHashMap.get(mKeys[i]));
//        }
//
//        mGatePassArrayList = gatePassArrayList;
//        mGatePassAdapter.addAll(mGatePassArrayList);
//        mGatePassAdapter.notifyDataSetChanged();

//        mGatePassListView.performItemClick(mGatePassListView.getChildAt(0), 0, 0);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    // Click Listeners
    private AdapterView.OnItemClickListener mGatePassListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            GatePass currentGatePass = mGatePassArrayList.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("gate_pass", currentGatePass);

            Fragment gatePassEditorFragment = new GatePassDetailFragment();
            gatePassEditorFragment.setArguments(bundle);
            FragmentTransaction  transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, gatePassEditorFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();

            Log.v(LOG_TAG, "ItemClicked = ( " + currentGatePass.getId() + " , " +
                    currentGatePass.getPersonName() + " , " +
                    currentGatePass.getContactPhone() + " , " +
                    currentGatePass.getDestination() + " )"
            );

        }
    } ;


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

    // Generating FakeData for Testing Purpose
    private void generateFakeGatePasses() {

        GatePass gatePassObject = new GatePass(1, "altaf driver", "12345678901", "department1store", "default remarks");
        GatePass gatePassObject1 = new GatePass(2, "Usman", "03023847938", "Karachi", "hello to future");

        mGatePassHashMap.put(gatePassObject.getId(), gatePassObject);
        mGatePassHashMap.put(gatePassObject1.getId(), gatePassObject1);

        Log.v(LOG_TAG, "id , name = ( " + gatePassObject.getId() + " , " + gatePassObject.getDestination() +  " )");

        Integer[] mKeys = mGatePassHashMap.keySet().toArray(new Integer[mGatePassHashMap.size()]);
        for (int i = 0; i < mGatePassHashMap.size(); i++) {
            GatePass gatePass = mGatePassHashMap.get(mKeys[i]);

            mGatePassArrayList.add(gatePass);

        }

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
//        mGatePassAdapter.clear();
//        mGatePassAdapter.addAll(gatePasses);

//        User user = new User(getContext());
//
//        Log.v(LOG_TAG, "token = " + user.getToken());


    }

}
