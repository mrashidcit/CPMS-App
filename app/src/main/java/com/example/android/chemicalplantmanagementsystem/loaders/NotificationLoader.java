package com.example.android.chemicalplantmanagementsystem.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

/**
 * Created by Rashid Saleem on 15-Feb-18.
 */

public class NotificationLoader extends AsyncTaskLoader{

    private static final String LOG_TAG = NotificationLoader.class.getSimpleName();
    private String mToken;
    private String mUrl;
    // request code can be " PUT, GET, POST, DELETE "
    private int mRequestCode;
    private String mJsonResponseString;

    private Bundle mArgs;

    public NotificationLoader(Context context, Bundle mArgs) {
        super(context);
        this.mArgs = mArgs;
        this.mUrl = mArgs.getString("url");
        this.mToken = mArgs.getString(UserContract.COLUMN_TOKEN);
        this.mRequestCode = mArgs.getInt(Api.REQUEST_CODE);
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
            return null;
        }

        // GET Request
        if (Api.CODE_GET_REQUEST == mRequestCode) {
            mJsonResponseString = QueryUtils.sendGetRequest(mUrl, mToken);

        }

        return mJsonResponseString;
    }
}
