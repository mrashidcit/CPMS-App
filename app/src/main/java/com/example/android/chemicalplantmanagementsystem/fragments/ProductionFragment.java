package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.DashBoard;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Production;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.ProductionAdapter;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract.ProductEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract.ProductionEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.loaders.ProductionLoader;
import com.example.android.chemicalplantmanagementsystem.network.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link ProductionFragment} factory method to
 * create an instance of this fragment.
 */
public class ProductionFragment extends Fragment
                        implements LoaderManager.LoaderCallbacks{

    private static final String LOG_TAG = ProductionFragment.class.getSimpleName();
    private static final int PRODUCTION_GET_DATA = 5001;

    // Data Members
    private HashMap<Integer, Production> mProductionHashMap;
    private ArrayList<Production> mProductionArrayList;
    private ProductionAdapter mProductionAdapter;
    private int mRequestCode;

    private Context mContext;
    private LoaderManager mLoaderManager;


    // View Elements
    private ListView mProductionListView;
    private ProgressBar mLoadingIndicatorView;
    private TextView mEmptyStateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View productionView = inflater.inflate(R.layout.fragment_production, container, false);

        // Initialize Data Members
        mContext = getContext();
        mLoaderManager = getLoaderManager();

        mProductionHashMap = new HashMap<Integer, Production>();
        mProductionArrayList = new ArrayList<Production>();
        mProductionAdapter = new ProductionAdapter(getContext(), new ArrayList<Production>());

        mProductionListView = (ListView) productionView.findViewById(R.id.list_production);
        mLoadingIndicatorView = (ProgressBar) productionView.findViewById(R.id.pb_loading_indicator);
        mEmptyStateView = (TextView) productionView.findViewById(R.id.tv_empty_view);

        mProductionListView.setEmptyView(mEmptyStateView);

        // Setting Click Listeners
        mProductionListView.setOnItemClickListener(mProductionListViewOnItemClickListener);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        mProductionListView.setAdapter(mProductionAdapter);

        return productionView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart()");
        fetchProductionData();
//        extractProductionDataFromJsonString();

//        mProductionListView.performItemClick(mProductionListView.getChildAt(2), 2, 2);

    }


    private void clearAllDataMembers() {
        mProductionHashMap.clear();
        mProductionArrayList.clear();
        mProductionAdapter.clear();

    }


    // Fetch Production Data from Network
    private void fetchProductionData() {


        mRequestCode = Api.CODE_GET_REQUEST;

        Boolean networkStatus = NetworkUtil.isConnectedToNetwork(mContext, LOG_TAG);

        if (networkStatus == true) {
            Bundle args = new Bundle();
            args.putString("url", Api.PRODUCTIONS_URL);
            mLoaderManager.initLoader(PRODUCTION_GET_DATA, args, this);

        } else {
            Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }

    }

    // ClickListener
    // Production List View Click Listener
    private AdapterView.OnItemClickListener mProductionListViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Production currentProduction = mProductionArrayList.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable(ProductionEntry.TABLE_NAME, currentProduction);

            // Create new Fragment and transaction
            Fragment newFragment = new ProductionDetailFragment();
            newFragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            // Replacte is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();

            // Changing Title of the Activity
            DashBoard dashBoard = (DashBoard) getActivity();
            dashBoard.getSupportActionBar().setTitle("Production Detail");

        }
    };


    // Extract Production Infrom Json
    private void extractProductionDataFromJsonString(String jsonString) {

        JSONObject root;
        JSONArray productionJsonArray;

        clearAllDataMembers();

        try {
            root = new JSONObject(jsonString);
            productionJsonArray = root.getJSONArray(ProductionEntry.TABLE_NAME);

            int id;
            String name;
            String productionCode;
            int status;
            String description;

            JSONObject productionJson;
            for (int i = 0; i < productionJsonArray.length(); i++) {
                productionJson = productionJsonArray.getJSONObject(i);

                // Getting Attributes from JsonObject
                id = productionJson.getInt(ProductionEntry._ID);
                name = productionJson.getString(ProductionEntry.COLUMN_NAME);
                productionCode = productionJson.getString(ProductionEntry.COLUMN_PRODUCTION_CODE);
                status = productionJson.getInt(ProductionEntry.COLUMN_STATUS);
                description = productionJson.getString(ProductionEntry.COLUMN_DESCRIPTION);

                Production production = new Production(id, name, productionCode, status, description);

                mProductionHashMap.put(id, production);
                mProductionArrayList.add(production);

            }

            mProductionAdapter.clear();
            mProductionAdapter.addAll(mProductionArrayList);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Used to Get Dummy Data
    private void extractProductionDataFromJsonString() {

        String jsonString = "{\"productions\":[{\"id\":5,\"name\":\"The name\",\"production_code\":\"XRHnjpDKlS\",\"status\":1,\"delete_status\":1,\"description\":\"production default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-01-19 14:44:29\",\"updated_at\":\"2018-01-19 14:44:29\"},{\"id\":2,\"name\":\"sdfesd\",\"production_code\":\"rVYJ02YImnJ\",\"status\":1,\"delete_status\":1,\"description\":\"gsdfesdf\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-02-14 10:59:12\",\"updated_at\":\"2018-02-14 10:59:12\"},{\"id\":3,\"name\":\"dsgesg\",\"production_code\":\"a2qRVs8NSiV\",\"status\":3,\"delete_status\":1,\"description\":\"gergsdf fsdf \",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}]}\n";



        JSONObject root;
        JSONArray productionJsonArray;

        try {
            root = new JSONObject(jsonString);
            productionJsonArray = root.getJSONArray(ProductionEntry.TABLE_NAME);

            clearAllDataMembers();

            int id;
            String name;
            String productionCode;
            int status;
            String description;

            JSONObject productionJson;
            for (int i = 0; i < productionJsonArray.length(); i++) {
                productionJson = productionJsonArray.getJSONObject(i);

                // Getting Attributes from JsonObject
                id = productionJson.getInt(ProductionEntry._ID);
                name = productionJson.getString(ProductionEntry.COLUMN_NAME);
                productionCode = productionJson.getString(ProductionEntry.COLUMN_PRODUCTION_CODE);
                status = productionJson.getInt(ProductionEntry.COLUMN_STATUS);
                description = productionJson.getString(ProductionEntry.COLUMN_DESCRIPTION);

                Production production = new Production(id, name, productionCode, status, description);

                mProductionHashMap.put(id, production);
                mProductionArrayList.add(production);

            }

            mProductionAdapter.clear();
            mProductionAdapter.addAll(mProductionArrayList);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        Log.v(LOG_TAG, "onCreateLoader()");

        mLoadingIndicatorView.setVisibility(View.VISIBLE); // show loading indicator
        mEmptyStateView.setText(""); // hide stat view

        if (Api.CODE_GET_REQUEST == mRequestCode) {

            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));
            args.putInt(Api.REQUEST_CODE, mRequestCode);

            return new ProductionLoader(mContext, args);

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.v(LOG_TAG, "onLoadFinished()");

        mLoadingIndicatorView.setVisibility(View.GONE); // Hiding Loading Indicator

        if (data == null ) {
            Log.v(LOG_TAG, "response data is null!");
            mEmptyStateView.setVisibility(View.VISIBLE); // Showing Status View
            mEmptyStateView.setText("Unable to connect to Server");
            Toast.makeText(mContext, "Unable to connect to Server", Toast.LENGTH_LONG).show();
        } else if(data.toString().isEmpty()) {
            Log.v(LOG_TAG, "response is empty!");
            mEmptyStateView.setVisibility(View.VISIBLE); // Showing Status View
            mEmptyStateView.setText("Unable to connect to Server");
            Toast.makeText(mContext, "Unable to connect to Server", Toast.LENGTH_LONG).show();
        }
        String jsonString = data.toString();

        JSONObject jsonResponse;

        try {
            jsonResponse = new JSONObject(jsonString);
            String status = jsonResponse.optString("status");

            if (status != null && status.equals("success")) {

                Log.v(LOG_TAG, "status: " + "Successfully Saved!");
                Toast.makeText(mContext, "Successfully Saved!", Toast.LENGTH_LONG).show();

//            clearAllFields(); // After Saving Data Clear All the Fields

            } else if (jsonResponse.optJSONArray(ProductionEntry.TABLE_NAME) != null){

                extractProductionDataFromJsonString(jsonString);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mLoaderManager.destroyLoader(PRODUCTION_GET_DATA);
        }

//        Log.v(LOG_TAG, "jsonString: " + jsonString);

        mLoaderManager.destroyLoader(PRODUCTION_GET_DATA);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
