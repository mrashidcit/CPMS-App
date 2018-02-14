package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.Production;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract.ProductionEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.loaders.DailyProductionLoader;
import com.example.android.chemicalplantmanagementsystem.network.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ProductionDetailFragment extends Fragment
                        implements LoaderManager.LoaderCallbacks {

    private static final String LOG_TAG = ProductionDetailFragment.class.getSimpleName();
    private static final int PRODUCTION_GET_DATA = 6001;

    // Data Members
    private Production mProduction;
    private HashMap<Integer, Product> mProductHashMap;

    private LoaderManager mLoaderManager;
    private Context mContext;
    private ViewGroup mViewGroup;

    // View Elements
    private LinearLayout mProductContainerView;
    private TextView mProductionCodeView;
    private TextView mProductionNameView;
    private TextView mProductionStatusView;
    private TextView mProductionDescriptionView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProduction = (Production) getArguments().getSerializable(ProductionEntry.TABLE_NAME);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_production_detail, container, false);

        // Initialize Data Members
        mProductHashMap = new HashMap<Integer, Product>();
        mLoaderManager = getLoaderManager();
        mContext = getContext();
        mViewGroup = container;

        // Assigning View to Data Members
        mProductContainerView = (LinearLayout) view.findViewById(R.id.ll_product_container);
        mProductionCodeView = (TextView) view.findViewById(R.id.tv_production_code);
        mProductionNameView = (TextView) view.findViewById(R.id.tv_name);
        mProductionStatusView = (TextView) view.findViewById(R.id.tv_status);
        mProductionDescriptionView = (TextView) view.findViewById(R.id.tv_description);

        // Showing Basic Production Info on Controls
        mProductionCodeView.setText(mProduction.getProductionCode());
        mProductionNameView.setText(mProduction.getProductionName());
        mProductionStatusView.setText(Production.getStatusMessage(mProduction.getProductionStatus()));
        mProductionDescriptionView.setText(mProduction.getDescription());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart()");
//        fetchProductionInfo();
        extractDataFromProductionJsonString();
        showProductsInList();

    }

    // Show Product List after fetching data from the Network
    private void showProductsInList() {

        Integer[] mKeys = mProductHashMap.keySet().toArray(new Integer[mProductHashMap.size()]);
        Product product;
        for (int i = 0; i < mKeys.length; i++) {

            product = mProductHashMap.get(mKeys[i]);

            addProductToList(product);

        }

    }


    private void addProductToList(Product product) {
        LinearLayout productItemLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.gate_pass_products_list_item, mViewGroup, false);

        TextView nameView = (TextView) productItemLinearLayout.getChildAt(0);
        TextView qtyView = (TextView) productItemLinearLayout.getChildAt(1);

        nameView.setText(product.getName());
        nameView.setTag(product.getId() + "");
        qtyView.setText(product.getPivotQuantity() + "");

        mProductContainerView.addView(productItemLinearLayout);
    }

    // Extract Product Detail from ProductionJsonString
    private void extractDataFromProductionJsonString() {

        String jsonString = "{\"productions\":{\"id\":3,\"name\":\"dsgesg\",\"production_code\":\"a2qRVs8NSiV\",\"status\":3,\"delete_status\":1,\"description\":\"gergsdf fsdf \",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 13:26:47\",\"products\":[{\"id\":1,\"product_code\":\"HPfQjt3NIr\",\"name\":\"product1\",\"delete_status\":1,\"description\":\"product1 default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\",\"pivot\":{\"production_id\":3,\"product_id\":1,\"quantity\":3,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}},{\"id\":3,\"product_code\":\"DwPQbJjtWxj\",\"name\":\"Air Fresher\",\"delete_status\":1,\"description\":\"sdf sdf efs esfd\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:58:41\",\"updated_at\":\"2018-01-30 21:58:41\",\"pivot\":{\"production_id\":3,\"product_id\":3,\"quantity\":4,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}},{\"id\":2,\"product_code\":\"eJYZ6ZYsqnb\",\"name\":\"Mineral Water\",\"delete_status\":1,\"description\":\"sfe sdfg esdfe fse\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:57:38\",\"updated_at\":\"2018-01-30 21:57:38\",\"pivot\":{\"production_id\":3,\"product_id\":2,\"quantity\":33,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}}]},\"products\":[{\"id\":1,\"product_code\":\"HPfQjt3NIr\",\"name\":\"product1\",\"delete_status\":1,\"description\":\"product1 default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\",\"pivot\":{\"production_id\":3,\"product_id\":1,\"quantity\":3,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}},{\"id\":3,\"product_code\":\"DwPQbJjtWxj\",\"name\":\"Air Fresher\",\"delete_status\":1,\"description\":\"sdf sdf efs esfd\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:58:41\",\"updated_at\":\"2018-01-30 21:58:41\",\"pivot\":{\"production_id\":3,\"product_id\":3,\"quantity\":4,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}},{\"id\":2,\"product_code\":\"eJYZ6ZYsqnb\",\"name\":\"Mineral Water\",\"delete_status\":1,\"description\":\"sfe sdfg esdfe fse\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:57:38\",\"updated_at\":\"2018-01-30 21:57:38\",\"pivot\":{\"production_id\":3,\"product_id\":2,\"quantity\":33,\"created_at\":\"2018-02-14 10:59:37\",\"updated_at\":\"2018-02-14 10:59:37\"}}]}\n";

        JSONObject root;
        JSONObject productionJsonObject;
        JSONArray productsJsonArray;

        try {
            root = new JSONObject(jsonString);
            productionJsonObject = root.getJSONObject(ProductionEntry.TABLE_NAME);

            productsJsonArray = productionJsonObject.getJSONArray(ProductContract.ProductEntry.TABLE_NAME);

            JSONObject productJson;
            int id;
            String name;
            int qty;
            for (int i = 0; i < productsJsonArray.length(); i++) {

                productJson = productsJsonArray.getJSONObject(i);

                id = productJson.getInt(ProductionEntry._ID);
                name = productJson.getString(ProductionEntry.COLUMN_NAME);
                qty = productJson.getJSONObject("pivot").getInt(ProductionEntry.PIVOT_QUANTITY);

                Product product = new Product(id, name, qty);

                mProductHashMap.put(id, product);
                Log.v(LOG_TAG, "id, name , qty: " +
                        product.getId() + " , " +
                        product.getName() + " , " +
                        product.getPivotQuantity()
                );

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void fetchProductionInfo() {

        int productionId = mProduction.getId();

        Boolean networkStatus = NetworkUtil.isConnectedToNetwork(getContext(), LOG_TAG);
        if (networkStatus == true) {
            Bundle args = new Bundle();
            args.putString("url", Api.PRODUCTIONS_URL + "/" + productionId);
            args.putInt(Api.REQUEST_CODE, Api.CODE_GET_REQUEST);

            mLoaderManager.initLoader(PRODUCTION_GET_DATA, args, this);

        } else {
            Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        Log.v(LOG_TAG, "onCreateLoader()");

        if (Api.CODE_GET_REQUEST == args.getInt(Api.REQUEST_CODE)) {

            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));

            return new DailyProductionLoader(mContext, args);

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

        Log.v(LOG_TAG, "jsonString: " + jsonString);

        return;

////        JSONObject jsonResponse;
////
////        try {
////
////            jsonResponse = new JSONObject(jsonString);
////
////            String status = jsonResponse.optString("status");
////
////            if (status != null && status.equals("success")) {
////
////                Log.v(LOG_TAG, "status: " + "Successfully Saved!");
////                Toast.makeText(mContext, "Successfully Saved!", Toast.LENGTH_LONG).show();
////
////            } else if (jsonResponse.optJSONArray(ProductContract.ProductEntry.TABLE_NAME) != null){
////
////            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            mLoaderManager.destroyLoader(PRODUCTION_GET_DATA);
//        }
//        mLoaderManager.destroyLoader(PRODUCTION_GET_DATA);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}