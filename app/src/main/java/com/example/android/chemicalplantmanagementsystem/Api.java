package com.example.android.chemicalplantmanagementsystem;

/**
 * Created by Rashid Saleem on 30-Jan-18.
 */

public class Api {


    public static final String REQUEST_CODE = "request_code";

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;
    public static final int CODE_PUT_REQUEST = 1026;


    private static final String ROOT_URL = "http://192.168.0.136:8000";

    public static final String API_ROOT_URL;

    static  {
        API_ROOT_URL = ROOT_URL + "/api";
    }

    private Api() {
        // hide implicit public constructor
    }

    // Gate Pass URLs
    public static final String GATE_PASS_URL = API_ROOT_URL + "/GatePass";


}
