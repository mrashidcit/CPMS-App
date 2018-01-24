package com.example.android.chemicalplantmanagementsystem.net;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Rashid Saleem on 24-Jan-18.
 */

public class UserLoader extends AsyncTaskLoader<String> {

    // Tag for log messages
    private static final String LOG_TAG = UserLoader.class.getSimpleName();

    // Query URL
    private String mUrl;

    /**
     * Constructs a new {@link UserLoader}
     * @param context
     * @param url
     */
    public UserLoader(Context context, String url) {
        super(context);
        mUrl = url;
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

        // Perform the network request, parse the response and extract the String of User

        String user = QueryUtils.fetchUserData(mUrl);

        return user;
    }
}
