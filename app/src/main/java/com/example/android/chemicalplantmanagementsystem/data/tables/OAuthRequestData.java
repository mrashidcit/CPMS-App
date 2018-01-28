package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public final class OAuthRequestData {

    // Key of the form-data fields
    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_SECRET_KEY = "client_secret";
    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String USER_NAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String SCOPE_KEY = "scope";


    public  int clientId;
    private String clientSecret;
    private String grantType;
    private String userName;
    private String password;
    private String scope;

    // Default Constructor
    public OAuthRequestData() {

        // Default Values
        clientId = 1;
        clientSecret = "yC1y6EZ4nFgxN5QKYoNfR0Mm7RQPldFzwNY6HoJ6";
        grantType = "password";
        scope = "*";

        this.userName = "muhammadmahindar@gmail.com";
        this.password = "secret";
    }

    // Setter
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    // Getter
    public int getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getScope() {
        return scope;
    }
}
