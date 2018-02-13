package com.example.android.chemicalplantmanagementsystem.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Rashid Saleem on 13-Feb-18.
 */

public class NetworkUtil {

    private NetworkUtil() {
    }


    public static final Boolean isConnectedToNetwork(Context context,  String logTag){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.v(logTag, "Connected to Network!");
            return true;
        } else {
            Log.v(logTag, "Not Connected to Network!");
            return false;
        }
    }

}
