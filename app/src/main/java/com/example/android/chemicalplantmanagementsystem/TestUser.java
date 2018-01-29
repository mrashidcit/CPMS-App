package com.example.android.chemicalplantmanagementsystem;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.network.UserLoader;

public class TestUser extends AppCompatActivity
                   implements LoaderManager.LoaderCallbacks<String> {

    private static final String LOG_TAG = TestUser.class.getSimpleName();

    private static final String TESTUSER_REQUEST_URL = "http://192.168.0.136:8000/api/user";

//    private static final String TESTUSER_REQUEST_URL = "http://192.168.137.205/api/user";

    private static final int TESTUSER_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_user);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(TESTUSER_LOADER_ID, null, this);

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        return new UserLoader(this, TESTUSER_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.v(LOG_TAG, data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
