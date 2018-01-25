package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public final class OAuthRequestData {

    // Key of the form-data fields
    private static final String clientIdKey = "client_id";
    private static final String clientSecretKey = "client_secret";
    private static final String grantTypeKey = "grant_type";
    private static final String userNameKey = "username";
    private static final String passwordKey = "password";
    private static final String scropeKey = "key";


    private int clientId;
    private String clientSecret;
    private String grantType;
    private String userName;
    private String password;
    private String scope;

    public OAuthRequestData(String userName, String password) {

        // Default Values
        clientId = 1;
        clientSecret = "yC1y6EZ4nFgxN5QKYoNfR0Mm7RQPldFzwNY6HoJ6";
        grantType = "password";
        scope = "*";

        this.userName = userName;
        this.password = password;
    }
}
