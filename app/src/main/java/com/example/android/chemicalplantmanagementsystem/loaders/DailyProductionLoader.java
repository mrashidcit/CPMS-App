package com.example.android.chemicalplantmanagementsystem.loaders;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.data.tables.DailyProduction;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.DailyProductionContract.DailyProductionEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Rashid Saleem on 13-Feb-18.
 */

public class DailyProductionLoader extends AsyncTaskLoader<String> {

    private static final String LOG_TAG = DailyProductionLoader.class.getSimpleName();

    private String mToken;
    private String mUrl;
    // request code can be " PUT, GET, POST, DELETE "
    private int mRequestCode;
    private String mJsonResponseString;

    private DailyProduction mDailyProduction;
    private Bundle mArgs;

    /**
     *
     * @param context
     * @param mArgs
     */
    public DailyProductionLoader(Context context, Bundle mArgs) {
        super(context);
        this.mArgs = mArgs;
        this.mUrl = mArgs.getString("url");
        this.mToken = mArgs.getString(UserContract.COLUMN_TOKEN);
        this.mRequestCode = mArgs.getInt(Api.REQUEST_CODE);
    }

    /**
     *
     * @param context
     * @param mDailyProduction
     * @param mArgs
     */
    public DailyProductionLoader(Context context, DailyProduction mDailyProduction, Bundle mArgs) {
        super(context);
        this.mDailyProduction = mDailyProduction;
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

        } else if (Api.CODE_POST_REQUEST == mRequestCode) {

//            Log.v(LOG_TAG, "")
            // Convert DailyProduction to JsonObject
            JSONObject dailyProductionJson;

            dailyProductionJson = new JSONObject();
            try {
                dailyProductionJson.put(DailyProductionEntry.COLUMN_PRODUCT_ID, mDailyProduction.getProductId());
                dailyProductionJson.put(DailyProductionEntry.COLUMN_PRODUCED, mDailyProduction.getProduced());
                dailyProductionJson.put(DailyProductionEntry.COLUMN_DISPATCHES, mDailyProduction.getDispatches());
                dailyProductionJson.put(DailyProductionEntry.COLUMN_SALE_RETURN, mDailyProduction.getSaleReturn());
                dailyProductionJson.put(DailyProductionEntry.COLUMN_RECEIVED, mDailyProduction.getReceived());

                mJsonResponseString = QueryUtils.sendPostRequest(mUrl, dailyProductionJson, mToken);

                Log.v(LOG_TAG, "mJsonResponseString: " + mJsonResponseString);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
