package com.example.android.chemicalplantmanagementsystem.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.data.tables.Production;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

/**
 * Created by Rashid Saleem on 14-Feb-18.
 */

public class ProductionLoader extends AsyncTaskLoader<String> {

    private static final String LOG_TAG = ProductionLoader.class.getSimpleName();

    private String mToken;
    private String mUrl;
    // request code can be " PUT, GET, POST, DELETE "
    private int mRequestCode;
    private String mJsonResponseString;

    private Production mProduction;
    private Bundle mArgs;

    /**
     *
     * @param context
     * @param mArgs
     */
    public ProductionLoader(Context context, Bundle mArgs) {
        super(context);
        this.mArgs = mArgs;
        this.mUrl = mArgs.getString("url");
        this.mToken = mArgs.getString(UserContract.COLUMN_TOKEN);
        this.mRequestCode = mArgs.getInt(Api.REQUEST_CODE);
    }

    /**
     *
     * @param context
     * @param mProduction
     * @param mArgs
     */
    public ProductionLoader(Context context, Production mProduction, Bundle mArgs) {
        super(context);
        this.mProduction = mProduction;
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
