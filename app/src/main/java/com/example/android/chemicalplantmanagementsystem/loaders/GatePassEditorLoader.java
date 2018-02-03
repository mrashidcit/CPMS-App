package com.example.android.chemicalplantmanagementsystem.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.GatePassContract.GatePassEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

import java.util.HashMap;

/**
 * Created by Rashid Saleem on 31-Jan-18.
 */

public class GatePassEditorLoader extends AsyncTaskLoader<String> {

    private String mToken;
    private String mUrl;

    // request code can be " PUT, GET, POST, DELETE "
    private int mRequestCode;

    private Bundle mArgs;

    // Response from the server
    private String mJsonResponseString;

    private HashMap<Integer, GatePass> mGatePassHashMap;


    // Constructor to get the GatePass Info. with particular id
    public GatePassEditorLoader(Context context, String url, Bundle args) {
        super(context);
        mUrl = url;
        mArgs = args;
        mRequestCode = args.getInt(Api.REQUEST_CODE);
        mToken = args.getString(UserContract.COLUMN_TOKEN);
        mGatePassHashMap = null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {

        if (mUrl == null) {
            return  null;
        }

        // Getting data of a particular GatePass
        if (mRequestCode == Api.CODE_GET_REQUEST) {

            mUrl += "/" + mArgs.getInt(GatePassEntry._ID);

            mJsonResponseString = QueryUtils.sendGetRequest(mUrl, mToken);

        }

        return mJsonResponseString;
    }

}
