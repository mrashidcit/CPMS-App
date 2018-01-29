package com.example.android.chemicalplantmanagementsystem.network;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Rashid Saleem on 24-Jan-18.
 */

public final class QueryUtils {

    // Tag for the log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    /**
     * Fetching User Data
     * @param requestUrl
     * @return
     */
    public static String fetchUserData(String requestUrl, String token) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url, token);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response
//        String testMessage = extractUserFromJson(jsonResponse);

        return jsonResponse;

    }


    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url, String token) throws IOException {


        String jsonString = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonString;
        }

        HttpURLConnection conn = null;
        InputStream inputStream = null;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", token);
//            conn.setRequestProperty("Authorization", token);

            conn.connect();



            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                jsonString = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + conn.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the User Result", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw and IOException which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException could be thrown.
                inputStream.close();
            }
        }
        return jsonString;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }


    private static String extractUserFromJson(String userJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(userJSON)) {
            return null;
        }

        // Create an empty String
        String user = "";

        // Try to parse the JSON response string. If there's a problem thrown.
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject parent = new JSONObject(userJSON);
            String msg = parent.getString("msg");

            user = msg;


        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the user JSON result", e);
        }

        return user;

    }

}






















