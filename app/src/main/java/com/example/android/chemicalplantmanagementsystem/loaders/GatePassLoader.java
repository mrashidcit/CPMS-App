package com.example.android.chemicalplantmanagementsystem.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

/**
 * Created by Rashid Saleem on 29-Jan-18.
 */

public class GatePassLoader extends android.support.v4.content.AsyncTaskLoader<String> {

    private static final String LOG_TAG = GatePassLoader.class.getSimpleName();

    // Query URL
    private String mUrl;
    private User mUser;

    public GatePassLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
        mUser = new User(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if (mUrl == null) {
            return null;
        }
//        String jsonResponse = GatePass.fetchGatePasses(mUrl);
        String jsonResponse = QueryUtils.fetchUserData(mUrl, mUser.getToken());

        return jsonResponse;
    }
}
