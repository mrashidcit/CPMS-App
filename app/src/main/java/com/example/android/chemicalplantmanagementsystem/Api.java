package com.example.android.chemicalplantmanagementsystem;

/**
 * Created by Rashid Saleem on 30-Jan-18.
 */

public class Api {

    public static final String REQUEST_CODE = "request_code";

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;
    public static final int CODE_PUT_REQUEST = 1026;

    public static final int CLIENT_ID = 1;
    public static final String CLIENT_SECRET = "yC1y6EZ4nFgxN5QKYoNfR0Mm7RQPldFzwNY6HoJ6";
    public static final String CLIENT_GRANT_TYPE = "password";
    public static final String CLIENT_SCOPE = "*";

    public static final String ROOT_URL = "http://192.168.1.3:8000";

    public static final String API_ROOT_URL;

    static  {
        API_ROOT_URL = ROOT_URL + "/api";
    }

    private Api() {
        // hide implicit public constructor
    }

    // Gate Pass URLs
    public static final String GATE_PASS_URL = API_ROOT_URL + "/GatePass";
    public static final String GATE_PASS_CREATE_URL = API_ROOT_URL + "/GatePass/create";

    // Daily Production URLs
    public static  final String DAILY_PRODUCTION_URL = API_ROOT_URL + "/DailyProduction";
    public static  final String DAILY_PRODUCTION_CREATE_URL = API_ROOT_URL + "/DailyProduction/create";

    // Productions URLs
    public static  final String PRODUCTIONS_URL = API_ROOT_URL + "/Production";
    public static  final String PRODUCTIONS_CREATE_URL = API_ROOT_URL + "/Production/create";


    // Production Notifications
    public static  final String PRODUCTION_NOTIFICATION_URL = API_ROOT_URL + "/Notifications";



}
