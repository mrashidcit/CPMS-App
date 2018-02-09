package com.example.android.chemicalplantmanagementsystem.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.Material;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.GatePassContract.GatePassEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.MaterialContract.MaterialEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract.ProductEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.fragments.GatePassEditorFragment;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;

/**
 * Created by Rashid Saleem on 31-Jan-18.
 */

public class GatePassEditorLoader extends AsyncTaskLoader<String> {

    private static final String LOG_TAG = GatePassEditorLoader.class.getSimpleName();
    private String mToken;
    private String mUrl;

    // request code can be " PUT, GET, POST, DELETE "
    private int mRequestCode;

    private Bundle mArgs;

    // Response from the server
    private String mJsonResponseString;

    private GatePass mGatePass;
    private HashMap<Integer, Product> mProductHashMap;
    private HashMap<Integer, Material> mMaterialHashMap;

    // Constructor to get the GatePass Info. with particular id
    // This will used to get Data from the Server
    public GatePassEditorLoader(Context context, String url, Bundle args) {
        super(context);
        mUrl = url;
        mArgs = args;
        mRequestCode = args.getInt(Api.REQUEST_CODE);
        mToken = args.getString(UserContract.COLUMN_TOKEN);
        mGatePass = null;
        mProductHashMap = null;
        mMaterialHashMap = null;
    }

    public GatePassEditorLoader(Context context, GatePass gatePass, HashMap<Integer, Product> productHashMap, HashMap<Integer, Material> materialHashMap, String url, Bundle args) {
        super(context);

        Log.v(LOG_TAG, "Constructor Called");
        mUrl = url;
        mArgs = args;
        mRequestCode = args.getInt(Api.REQUEST_CODE);
        mToken = args.getString(UserContract.COLUMN_TOKEN);

        mGatePass =  gatePass;
        mProductHashMap = productHashMap;
        mMaterialHashMap = materialHashMap;

    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        Log.v(LOG_TAG, "loadInBackground()");

        if (mUrl == null) {
            return  null;
        }

        // GET Request
        if (Api.CODE_GET_REQUEST == mRequestCode) {

            mJsonResponseString = QueryUtils.sendGetRequest(mUrl, mToken);
            Log.v(LOG_TAG, "mJsonResponseString: " + mJsonResponseString);


        } else if (Api.CODE_POST_REQUEST == mRequestCode) {
            // Getting data of a particular GatePass
            JSONObject root = new JSONObject();
            JSONObject gatePassJson = new JSONObject();
            JSONArray productJsonArray = new JSONArray();
            JSONArray materialJsonArray = new JSONArray();

            try {
                // GatePass Object Info.
                gatePassJson.put(GatePassEntry._ID, mGatePass.getId());
                gatePassJson.put(GatePassEntry.COLUMN_PERSON_NAME, mGatePass.getPersonName());
                gatePassJson.put(GatePassEntry.COLUMN_CONTACT_PHONE, mGatePass.getContactPhone());
                gatePassJson.put(GatePassEntry.COLUMN_DESTINATION, mGatePass.getDestination());
                gatePassJson.put(GatePassEntry.COLUMN_REMARKS, mGatePass.getRemarks());

                // For Product JSONArray
                Integer[] productKeys = mProductHashMap.keySet().toArray(new Integer[mProductHashMap.size()]);
                Product product;
                JSONObject productJson;

                for (int i = 0; i < productKeys.length; i++) {

                    product = mProductHashMap.get(productKeys[i]);

                    productJson = new JSONObject();

                    productJson.put(ProductEntry.COLUMN_PIVOT_PRODUCT_ID, product.getPivotProductId());
                    productJson.put(ProductEntry.COLUMN_PIVOT_QTY, product.getPivotQuantity());

                    productJsonArray.put(i, productJson);

                }

                // For Material JSONArray
                Integer [] materialKeys = mMaterialHashMap.keySet().toArray(new Integer[mMaterialHashMap.size()]);
                Material material = null;
                JSONObject materialJson = null;

                for (int i=0; i < materialKeys.length; i++) {

                    material = mMaterialHashMap.get(materialKeys[i]);

                    materialJson = new JSONObject();
                    materialJson.put(MaterialEntry.COLUMN_PIVOT_MATERIAL_ID, material.getPivotMaterialId());
                    materialJson.put(MaterialEntry.COLUMN_PIVOT_QUANTITY , material.getPivotMaterialId());

                    materialJsonArray.put(i, materialJson);

                }

                root.put(GatePassEntry.TABLE_NAME, gatePassJson); // Add GatePass Json
                root.put(ProductEntry.TABLE_NAME, productJsonArray); // Add Product JSONArray
                root.put(MaterialEntry.TABLE_NAME, materialJsonArray); // Add Material JSONArray

//            Log.v(LOG_TAG, root.toString());
                Log.v(LOG_TAG, "url: " + mUrl);
                Log.v(LOG_TAG, "token: " + mToken);

                mJsonResponseString = QueryUtils.sendPostRequest(mUrl, root, mToken);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return mJsonResponseString;
    }

}
