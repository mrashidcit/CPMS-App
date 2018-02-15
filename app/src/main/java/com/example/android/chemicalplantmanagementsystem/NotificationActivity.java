package com.example.android.chemicalplantmanagementsystem;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.data.tables.Production;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.adapters.ProductionAdapter;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract.ProductionEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.fragments.ProductionDetailFragment;
import com.example.android.chemicalplantmanagementsystem.loaders.NotificationLoader;
import com.example.android.chemicalplantmanagementsystem.network.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks {

    private static final String LOG_TAG = NotificationActivity.class.getSimpleName();
    private static final int PRODUCTION_GET_DATA = 7001;

    private Context mContext;
    private LoaderManager mLoaderManager;
    private ArrayList<Production> mPendingProudctionArrayList;
    private ArrayList<Production> mApprovedProudctionArrayList;
    private ArrayList<Production> mCompletedProudctionArrayList;

    private ProductionAdapter mProductionAdapter;
    private ListView mProductionListView;
    private ProgressBar mLoadingIndicatorView;
    private TextView mEmptyStateView;
    private TextView mTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Initialize Data Members
        mContext = getBaseContext();
        mLoaderManager = getLoaderManager();
        mProductionAdapter = new ProductionAdapter(mContext, new ArrayList<Production>());
        mPendingProudctionArrayList = new ArrayList<Production>();
        mApprovedProudctionArrayList = new ArrayList<Production>();
        mCompletedProudctionArrayList = new ArrayList<Production>();

        // Setting View's elements
        mProductionListView = findViewById(R.id.lv_productions);
        mLoadingIndicatorView = findViewById(R.id.pb_loading_indicator);
        mEmptyStateView = findViewById(R.id.tv_empty_view);

        mProductionListView.setEmptyView(mEmptyStateView);
        mProductionListView.setOnItemClickListener(mProductionListViewClickListener);
        mProductionListView.setAdapter(mProductionAdapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchProductionsInfo();
//        extractNotifcationDataFromJson();
    }

    // Extract Production Info from JsonString
    private void extractNotifcationDataFromJson(String jsonString) {

        JSONObject root;

        JSONArray pendingJsonArray;
        JSONArray approvedJsonArray;
        JSONArray completedJsonArray;

        try {
            root = new JSONObject(jsonString);
            pendingJsonArray = root.getJSONArray(ProductionContract.PENDING_PRODUCTION);
            approvedJsonArray = root.getJSONArray(ProductionContract.APPRVOED_PRODUCTION);
            completedJsonArray = root.getJSONArray(ProductionContract.COMPLETED_PRODUCTION);

            mPendingProudctionArrayList = convertProductionJsonArraytoArrayList(pendingJsonArray);
            mApprovedProudctionArrayList = convertProductionJsonArraytoArrayList(approvedJsonArray);
            mCompletedProudctionArrayList = convertProductionJsonArraytoArrayList(completedJsonArray);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        mProductionAdapter.addAll(mPendingProudctionArrayList);
        mProductionAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mProductionListView.getVisibility() != View.VISIBLE ) {
            mProductionListView.setVisibility(View.VISIBLE);
        }

        Log.v(LOG_TAG, "NotificationActivity onBackPressed()");
    }

    // Extract Production Info
    private void extractNotifcationDataFromJson() {

        String jsonString = "{\"pending\":[{\"id\":1,\"name\":\"The name\",\"production_code\":\"XRHnjpDKlS\",\"status\":1,\"delete_status\":1,\"description\":\"production default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-01-19 14:44:29\",\"updated_at\":\"2018-01-19 14:44:29\"},{\"id\":2,\"name\":\"sdfesd\",\"production_code\":\"rVYJ02YImnJ\",\"status\":1,\"delete_status\":1,\"description\":\"gsdfesdf\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-02-14 10:59:12\",\"updated_at\":\"2018-02-14 10:59:12\"}],\"approved\":[{\"id\":3,\"name\":\"dsgesg\",\"production_code\":\"a2qRVs8NSiV\",\"status\":3,\"delete_status\":1,\"description\":\"gergsdf fsdf \",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 13:26:47\"}],\"completed\":[]}";


        JSONObject root;

        JSONArray pendingJsonArray;
        JSONArray approvedJsonArray;
        JSONArray completedJsonArray;

        try {
            root = new JSONObject(jsonString);
            pendingJsonArray = root.getJSONArray(ProductionContract.PENDING_PRODUCTION);
            approvedJsonArray = root.getJSONArray(ProductionContract.APPRVOED_PRODUCTION);
            completedJsonArray = root.getJSONArray(ProductionContract.COMPLETED_PRODUCTION);

            mPendingProudctionArrayList = convertProductionJsonArraytoArrayList(pendingJsonArray);
            mApprovedProudctionArrayList = convertProductionJsonArraytoArrayList(approvedJsonArray);
            mCompletedProudctionArrayList = convertProductionJsonArraytoArrayList(completedJsonArray);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        mProductionAdapter.addAll(mPendingProudctionArrayList);
        mProductionAdapter.notifyDataSetChanged();

    }

    // Click Listeners
    private AdapterView.OnItemClickListener mProductionListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            mProductionListView.setVisibility(View.GONE);


            Production currentProduction = mProductionAdapter.getItem(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable(ProductionEntry.TABLE_NAME, currentProduction);

            Fragment newFragment = new ProductionDetailFragment();
            newFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            getSupportActionBar().setTitle("Production Detail");
        }
    };


    // Navgation Item Selected Listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            mProductionAdapter.clear();
            mProductionListView.setAdapter(mProductionAdapter);

            switch (item.getItemId()) {

                case R.id.navigation_pending:

                    mProductionAdapter.addAll(mPendingProudctionArrayList);
                    mProductionAdapter.notifyDataSetChanged();

                    return true;
                case R.id.navigation_approved:

                    mProductionAdapter.addAll(mApprovedProudctionArrayList);
                    mProductionAdapter.notifyDataSetChanged();


                    return true;
                case R.id.navigation_completed:
                    mProductionAdapter.addAll(mCompletedProudctionArrayList);
                    mProductionAdapter.notifyDataSetChanged();

                    return true;
            }
            return false;
        }
    };

    // Convert ProductionJsonArray into ArrayList()
    private ArrayList<Production> convertProductionJsonArraytoArrayList(JSONArray productionJsonArray) {

        ArrayList<Production> productionArrayList  = new ArrayList<Production>();

        JSONObject productionJson;
        int id;
        String name;
        String productionCode;
        int status;
        String description;
        try {
            for (int i = 0; i < productionJsonArray.length(); i++) {

                productionJson = productionJsonArray.getJSONObject(i);

                id = productionJson.getInt(ProductionEntry._ID);
                name = productionJson.getString(ProductionEntry.COLUMN_NAME);
                productionCode = productionJson.getString(ProductionEntry.COLUMN_PRODUCTION_CODE);
                status = productionJson.getInt(ProductionEntry.COLUMN_STATUS);
                description = productionJson.getString(ProductionEntry.COLUMN_DESCRIPTION);

                Production production = new Production(id, name, productionCode, status, description);

                productionArrayList.add(production);

                Log.v(LOG_TAG, "name: " + productionJson.getString(ProductionEntry.COLUMN_NAME));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productionArrayList;
    }

    // fetch Production Info.
    private void fetchProductionsInfo() {

        Boolean networkStatus = NetworkUtil.isConnectedToNetwork(mContext, LOG_TAG);

        if (networkStatus == true) {
            Bundle args = new Bundle();
            args.putInt(Api.REQUEST_CODE, Api.CODE_GET_REQUEST);
            args.putString("url", Api.PRODUCTION_NOTIFICATION_URL);
            mLoaderManager.initLoader(PRODUCTION_GET_DATA, args, this);

        } else {
            Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "onCreateLoader()");

        mLoadingIndicatorView.setVisibility(View.VISIBLE); // show loading indicator
        mEmptyStateView.setText(""); // hide stat view

        if (Api.CODE_GET_REQUEST == args.getInt(Api.REQUEST_CODE)) {

            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));

            return new NotificationLoader(mContext, args);

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.v(LOG_TAG, "onLoadFinished()");

        mLoadingIndicatorView.setVisibility(View.GONE); // Hiding Loading Indicator

        if (data == null) {
            Log.v(LOG_TAG, "response data is null!");
            mEmptyStateView.setVisibility(View.VISIBLE); // Showing Status View
            mEmptyStateView.setText("Unable to connect to Server. Reconnect to your Wifi again.");
            Toast.makeText(mContext, "Unable to connect to Server", Toast.LENGTH_LONG).show();
        } else if (data.toString().isEmpty()) {
            Log.v(LOG_TAG, "response is empty!");
            mEmptyStateView.setVisibility(View.VISIBLE); // Showing Status View
            mEmptyStateView.setText("Unable to connect to Server. . Reconnect to your Wifi again.");
            Toast.makeText(mContext, "Unable to connect to Server. Reconnect to your Wifi again.", Toast.LENGTH_LONG).show();
        }
        String jsonString = data.toString();

        extractNotifcationDataFromJson(jsonString);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
