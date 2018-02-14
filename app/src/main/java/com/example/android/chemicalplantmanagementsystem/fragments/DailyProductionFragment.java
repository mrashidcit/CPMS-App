package com.example.android.chemicalplantmanagementsystem.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.DailyProduction;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract.ProductEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.loaders.DailyProductionLoader;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassEditorLoader;
import com.example.android.chemicalplantmanagementsystem.network.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyProductionFragment} factory method to
 * create an instance of this fragment.
 */
public class DailyProductionFragment extends Fragment
                    implements LoaderManager.LoaderCallbacks{

    private static final String LOG_TAG = DailyProductionFragment.class.getSimpleName();
    private static final int DAILY_PRODCUTION_GET_PRODUCTS =  4001;
    private static final int DAILY_PRODCUTION_POST_DATA = 4002;

    private Button mSubmitView;
    private View mSelectProductContainer;
    private Context mContext;
    private LoaderManager mLoaderManager;
    private int mRequestCode;
    private DailyProduction mDailyProduction;

    private Spinner mProductSpinner;
    private TextView mProductNameView;
    private TextView  mProducedView;
    private TextView  mDispatchesView;
    private TextView  mSaleReturnView;
    private TextView  mReceivedView;
    private Button mSaveButtonView;
    private Button mBackToProductSelection;
    private View mDailyProductionFormContainer;

    private ArrayList<Product> mProductArrayList;
    private ArrayAdapter<String> mProductNamesArrayAdapter; // Used for Spinner

    private int mCurrentProductPosition;

    private Product mCurrentProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_daily_production, container, false);

        // Getting Control Views Objects
        mProductNameView = (TextView) view.findViewById(R.id.tv_product_name);
        mProducedView = (TextView) view.findViewById(R.id.et_produced);
        mDispatchesView = (TextView) view.findViewById(R.id.et_dispatches);
        mSaleReturnView = (TextView) view.findViewById(R.id.et_sale_return);
        mReceivedView = (TextView) view.findViewById(R.id.et_received);
        mSaveButtonView = (Button) view.findViewById(R.id.btn_save);
        mBackToProductSelection = (Button) view.findViewById(R.id.btn_back_to_products_selection);

        // Setting Click Listeners
        mSaveButtonView.setOnClickListener(mSaveClickListener);
        mBackToProductSelection.setOnClickListener(mBackToProductSelectionClickListener);

        // Initializer the Data Members
        mContext = getContext();
        mLoaderManager = getLoaderManager();
        mProductArrayList = new ArrayList<Product>();
        mProductNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);

        // Containers
        mSelectProductContainer = view.findViewById(R.id.container_select_product);
        mDailyProductionFormContainer = view.findViewById(R.id.container_daily_production_form);

        mProductSpinner = (Spinner) view.findViewById(R.id.products_spinner);

        mSubmitView = (Button) view.findViewById(R.id.btn_submit);

        mSubmitView.setOnClickListener(mSubmitClickListener);

        mProductSpinner.setAdapter(mProductNamesArrayAdapter);
        mProductSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);

        // Insert Dummy Data in Fields
//        mProducedView.setText(2 + "");
//        mDispatchesView.setText(23 + "");
//        mSaleReturnView.setText(4 + "");
//        mReceivedView.setText(5 + "");

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart()");
//        extractProductsFromJson();
        fetchProducts();

    }

    @Override
    public void onResume() {
        super.onResume();

//        mSubmitView.performClick();
    }

    // Clear All Fields after Saving Data
    private void clearAllFields() {

        mProducedView.setText("");
        mDispatchesView.setText("");
        mSaleReturnView.setText("");
        mReceivedView.setText("");
    }

    // Extract Product Data from JsonString
//    private void extractProductsFromJson() {
    private void extractProductsFromJson(String jsonString) {
//        String jsonString = "[{\"id\":1,\"product_code\":\"HPfQjt3NIr\",\"name\":\"product1\",\"delete_status\":1,\"description\":\"product1 default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\"},{\"id\":2,\"product_code\":\"eJYZ6ZYsqnb\",\"name\":\"Mineral Water\",\"delete_status\":1,\"description\":\"sfe sdfg esdfe fse\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:57:38\",\"updated_at\":\"2018-01-30 21:57:38\"},{\"id\":3,\"product_code\":\"DwPQbJjtWxj\",\"name\":\"Air Fresher\",\"delete_status\":1,\"description\":\"sdf sdf efs esfd\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:58:41\",\"updated_at\":\"2018-01-30 21:58:41\"}]";

        JSONObject root;
        JSONArray productsJsonArray ;

        try {
            root = new JSONObject(jsonString);

            productsJsonArray = root.optJSONArray(ProductEntry.TABLE_NAME);

            JSONObject productObject;
            int id ;
            String name;
            Product product;
            for (int i = 0; i < productsJsonArray.length(); i++) {
                productObject = productsJsonArray.getJSONObject(i);

                id   = productObject.getInt(ProductEntry._ID);
                name = productObject.getString(ProductEntry.COLUMN_NAME);

                product = new Product(id, name);
                mProductArrayList.add(product);

            }

            for (int i = 0; i < mProductArrayList.size(); i++) {
                mProductNamesArrayAdapter.add(mProductArrayList.get(i).getName());
            }
            mProductNamesArrayAdapter.notifyDataSetChanged();
            mCurrentProductPosition = 0;


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Used for Dummy Data Only
    private void  extractProductsFromJson() {
        String jsonString = "[{\"id\":1,\"product_code\":\"HPfQjt3NIr\",\"name\":\"product1\",\"delete_status\":1,\"description\":\"product1 default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\"},{\"id\":2,\"product_code\":\"eJYZ6ZYsqnb\",\"name\":\"Mineral Water\",\"delete_status\":1,\"description\":\"sfe sdfg esdfe fse\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:57:38\",\"updated_at\":\"2018-01-30 21:57:38\"},{\"id\":3,\"product_code\":\"DwPQbJjtWxj\",\"name\":\"Air Fresher\",\"delete_status\":1,\"description\":\"sdf sdf efs esfd\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:58:41\",\"updated_at\":\"2018-01-30 21:58:41\"}]";

        JSONArray productsJsonArray ;

        try {
            productsJsonArray = new JSONArray(jsonString);

            JSONObject productObject;
            int id ;
            String name;
            Product product;
            for (int i = 0; i < productsJsonArray.length(); i++) {
                productObject = productsJsonArray.getJSONObject(i);

                id   = productObject.getInt(ProductEntry._ID);
                name = productObject.getString(ProductEntry.COLUMN_NAME);

                product = new Product(id, name);
                mProductArrayList.add(product);

            }

            for (int i = 0; i < mProductArrayList.size(); i++) {
                mProductNamesArrayAdapter.add(mProductArrayList.get(i).getName());
            }
            mProductNamesArrayAdapter.notifyDataSetChanged();
            mCurrentProductPosition = 0;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mCurrentProduct = mProductArrayList.get(mCurrentProductPosition);

        Log.v(LOG_TAG, "id , name: " + mCurrentProduct.getId() + " , " + mCurrentProduct.getName());

//        mSubmitView.performClick();

    }

    private void fetchProducts() {
        mRequestCode = Api.CODE_GET_REQUEST;
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle args = new Bundle();
            args.putString("url", Api.DAILY_PRODUCTION_CREATE_URL);
            mLoaderManager.initLoader(DAILY_PRODCUTION_GET_PRODUCTS, args, this);

        } else {

            Toast.makeText(mContext, "Unable to Connect to Network!" , Toast.LENGTH_LONG).show();
            Log.v(LOG_TAG, "Unable to Connect to Network!");
        }
    }



    // Click Listeners
    private AdapterView.OnItemSelectedListener spinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Log.v("MainActivity", parent.getItemAtPosition(position) + "" +
//                                        "id = " + id );

            // Storing current Position of the product for temporary use
            mCurrentProductPosition = position;
            Product currentProduct = mProductArrayList.get(position);


            Log.v("MainActivity", "(id, name)" + "(" +
                    currentProduct.getId() + " , " +
                    currentProduct.getName()  + "d )"
            );

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Click Listener for Submit Button
    private View.OnClickListener mSubmitClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mCurrentProductPosition = mProductSpinner.getSelectedItemPosition();

            mCurrentProduct = mProductArrayList.get(mCurrentProductPosition);

//            Log.v(LOG_TAG, "id , name: " + mCurrentProduct.getId() + " , " + mCurrentProduct.getName());

            // Hiding container_select_product
            mSelectProductContainer.setVisibility(View.GONE);

            // Showing container_daily_production_form
            mDailyProductionFormContainer.setVisibility(View.VISIBLE);
            mProductNameView.setText(mCurrentProduct.getName());

        }
    };

    //  Toggle View of DailyProductionFormContainer
    private void toggleDailyProductionFormContainer() {

        if (mDailyProductionFormContainer.getVisibility() == View.VISIBLE) {
            mDailyProductionFormContainer.setVisibility(View.GONE);

        } else {
            mDailyProductionFormContainer.setVisibility(View.VISIBLE);
        }
    }

    // Toggle View of SelectProductContainer
    private void toggleSelectProductContainer() {

        if (mSelectProductContainer.getVisibility() == View.VISIBLE) {
            mSelectProductContainer.setVisibility(View.GONE);
        } else {
            mSelectProductContainer.setVisibility(View.VISIBLE);
        }

    }

    // Back to Product Selection
    private View.OnClickListener mBackToProductSelectionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            toggleDailyProductionFormContainer(); // Hiding the Daily ProductionFromContainer
            toggleSelectProductContainer();

        }
    };

    // Save Button click Listener
    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int productId = (int) mCurrentProduct.getId();
            int produced = Integer.parseInt(mProducedView.getText().toString());
            int dispatches = Integer.parseInt(mDispatchesView.getText().toString());
            int saleReturn = Integer.parseInt(mSaleReturnView.getText().toString());
            int received = Integer.parseInt(mReceivedView.getText().toString());

            DailyProduction dailyProduction = new DailyProduction(productId, produced, dispatches, saleReturn, received);

//            Log.v(LOG_TAG, "(id, produced, dispatches, saleReturn, received) = ( " +
//                    dailyProduction.getProductId() + " , " +
//                    dailyProduction.getProduced() + " , " +
//                    dailyProduction.getDispatches() + " , " +
//                    dailyProduction.getSaleReturn() + " , " +
//                    dailyProduction.getReceived() + " ) "
//            );

            saveDailyProduction();


        }
    };

    private void saveDailyProduction() {

        int productId = mCurrentProduct.getId();
        int produced = Integer.parseInt(mProducedView.getText().toString());
        int dispatches = Integer.parseInt(mDispatchesView.getText().toString());
        int saleReturn = Integer.parseInt(mSaleReturnView.getText().toString());
        int received = Integer.parseInt(mReceivedView.getText().toString());

        mDailyProduction = new DailyProduction(productId, produced, dispatches, saleReturn, received);

        Log.v(LOG_TAG, "saveDailyProduction()");

        mRequestCode = Api.CODE_POST_REQUEST;
        Boolean networkStatus =  NetworkUtil.isConnectedToNetwork(mContext, LOG_TAG);
        if (networkStatus == true) {
            Bundle args = new Bundle();
            args.putString("url", Api.DAILY_PRODUCTION_URL);

            mLoaderManager.initLoader(DAILY_PRODCUTION_POST_DATA, args, this);

        } else {
            Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        Log.v(LOG_TAG, "onCreateLoader()");

        if (Api.CODE_GET_REQUEST == mRequestCode) {

            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));
            args.putInt(Api.REQUEST_CODE, mRequestCode);

            return  new DailyProductionLoader(mContext, args);

        } else if (Api.CODE_POST_REQUEST == mRequestCode) {

            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));
            args.putInt(Api.REQUEST_CODE, mRequestCode);

            return new DailyProductionLoader(mContext, mDailyProduction, args);
        }

        return null;
    }


    @Override
    public void onLoadFinished(Loader loader, Object data) {

        Log.v(LOG_TAG, "onLoadFinished()");

        if (data == null ) {
            Log.v(LOG_TAG, "response data is null!");
            Toast.makeText(mContext, "Unable to connect to Server", Toast.LENGTH_LONG).show();
        } else if(data.toString().isEmpty()) {
            Log.v(LOG_TAG, "response is empty!");
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

                clearAllFields(); // After Saving Data Clear All the Fields

            } else if (jsonResponse.optJSONArray(ProductEntry.TABLE_NAME) != null){

                extractProductsFromJson(jsonString);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mLoaderManager.destroyLoader(DAILY_PRODCUTION_POST_DATA);
        }

        mLoaderManager.destroyLoader(DAILY_PRODCUTION_POST_DATA);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
