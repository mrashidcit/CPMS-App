package com.example.android.chemicalplantmanagementsystem.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.sax.RootElement;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.GatePassContract.GatePassEntry;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rashid Saleem on 29-Jan-18.
 */

public class GatePassLoader extends android.support.v4.content.AsyncTaskLoader<HashMap<Integer, GatePass>> {

    private static final String LOG_TAG = GatePassLoader.class.getSimpleName();


    // Query URL
    private String mUrl;
    private User mUser;
    private String mToken;

    // the parameters
    HashMap<Integer, GatePass> params;

    //the request code to define whether it is a GET or POST
    int requestCode;

    public GatePassLoader(Context context, String mUrl, int requestCode) {
        super(context);
        mUser = new User(context);
        this.mUrl = mUrl;
        this.params = null;
        this.requestCode = requestCode;

        mToken = mUser.getToken();

    }

    public GatePassLoader(Context context, String mUrl, HashMap<Integer, GatePass> params, int requestCode) {
        super(context);
        mUser = new User(context);
        this.mUrl = mUrl;
        this.params = params;
        this.requestCode = requestCode;

        mToken = mUser.getToken();

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public HashMap<Integer, GatePass> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        String jsonString = null;
        if (requestCode == Api.CODE_GET_REQUEST) {

            jsonString = QueryUtils.sendGetRequest(mUrl, mToken);

            if(TextUtils.isEmpty(jsonString)) {
                return null;
            }

            return convertGatePassJSONStringToHashMap(jsonString);

        } else if (requestCode == Api.CODE_POST_REQUEST) {

        } else if (requestCode == Api.CODE_PUT_REQUEST) {
            GatePass gatePass = params.get(params.keySet().toArray()[0]);
            JSONObject gatePassJson = convertGatePasstoJsonObject(gatePass);

            jsonString = QueryUtils.sendPutRequest(mUrl, gatePassJson, mToken);

            if(TextUtils.isEmpty(jsonString)) {
                return null;
            }

            JSONObject json = null;
            try {
                json = new JSONObject(jsonString);

                gatePassJson = json.getJSONObject("gatepass");

                // Convert JsonObject to GatePass Object
                json = gatePassJson;

                int id = json.getInt(GatePassEntry._ID);
                String personName = json.getString(GatePassEntry.COLUMN_PERSON_NAME);
                String contactPhone = json.getString(GatePassEntry.COLUMN_CONTACT_PHONE);
                String destination = json.getString(GatePassEntry.COLUMN_DESTINATION);
                String remarks = json.getString(GatePassEntry.COLUMN_REMARKS);

                gatePass = new GatePass(id, personName, contactPhone, destination, remarks);

                HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();

                gatePassHashMap.put(gatePass.getId(), gatePass);

                return gatePassHashMap;

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        return null;

    }

    // Convert GatePasse Json String to HashMap
    public HashMap<Integer, GatePass> convertGatePassJSONStringToHashMap(String jsonResponse) {
        JSONArray gatePasses = null;
        JSONObject gatePass;

        HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();

        try {
            gatePasses = new JSONArray(jsonResponse);

            for (int i = 0; i < gatePasses.length(); i++) {
                gatePass = gatePasses.getJSONObject(i);

                int id = gatePass.getInt("id");
                String personName = gatePass.getString("person_name");
                String contactPhone = gatePass.getString("contact_phone");
                String destination = gatePass.getString("destination");
                String remarks = gatePass.getString("remarks");

                GatePass gatePassObject = new GatePass(id, personName, contactPhone, destination, remarks);

                gatePassHashMap.put(id, gatePassObject);

                Log.v("GatePassObject", "(id, personName, contactPhone, remarks ) = (" + id + " , " +
                        personName + " , " + contactPhone + " , " + remarks + " )"
                );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return gatePassHashMap;
    }

    // Convert GatePass Object to JSONObject
    public static JSONObject convertGatePasstoJsonObject(GatePass gatePass) {
        JSONObject json = new JSONObject();

        try {
            json.put(GatePassEntry._ID, gatePass.getId());
            json.put(GatePassEntry.COLUMN_PERSON_NAME, gatePass.getPersonName());
            json.put(GatePassEntry.COLUMN_CONTACT_PHONE, gatePass.getContactPhone());
            json.put(GatePassEntry.COLUMN_DESTINATION, gatePass.getDestination());
            json.put(GatePassEntry.COLUMN_REMARKS, gatePass.getRemarks());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

}
