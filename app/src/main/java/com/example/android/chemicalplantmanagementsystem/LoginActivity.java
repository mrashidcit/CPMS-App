package com.example.android.chemicalplantmanagementsystem;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.chemicalplantmanagementsystem.data.tables.OAuthRequestData;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract.UserEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String PREFS_NAME = "LoginActivity";

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.username);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        // requesting for token
        mAuthTask = new UserLoginTask("hello", "world");
        mAuthTask.execute((Void) null);

//        SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
//        String username = pref.getString(getString(R.string.pref_username_key), "");
//        String password = pref.getString(getString(R.string.pref_password_key), "");

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final int mClientId;
        private final String mClientSecret;
        private final String mGrantType;
        private final String mEmail;
        private final String mPassword;
        private final String mScope;

        private final OAuthRequestData mOAuthRequestData;


        UserLoginTask(String email, String password) {
            mOAuthRequestData = new OAuthRequestData();

            // Default Values
            mClientId = 1;
            mClientSecret = Api.CLIENT_SECRET;
            mGrantType = "password";
            mScope = "*";

            mEmail = mOAuthRequestData.getUserName();
            mPassword = mOAuthRequestData.getPassword();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            URL url;
            URL urlUserInfo;
            String data = "";
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            JSONObject json = new JSONObject();
            JSONObject responseJsonObject = null;


            try {
                url = new URL("http://" + getString(R.string.host_address).toString() + "/oauth/token");
                urlUserInfo = new URL("http://" + getString(R.string.host_address).toString() + "/api/userInfo");
//                url = new URL("http://" + getString(R.string.host_address).toString() + "/api/user");
//                url = new URL("http://" + getString(R.string.host_address).toString() + "/api/test");

                json.put(OAuthRequestData.CLIENT_ID_KEY, mClientId);
                json.put(OAuthRequestData.CLIENT_SECRET_KEY, mClientSecret);
                json.put(OAuthRequestData.GRANT_TYPE_KEY, mGrantType);
                json.put(OAuthRequestData.USER_NAME_KEY, mEmail);
                json.put(OAuthRequestData.PASSWORD_KEY, mPassword);
                json.put(OAuthRequestData.SCOPE_KEY, mScope);

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");

                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

                osw.write(json.toString());
                osw.flush();


                if (conn.getResponseCode() == 200) {
                    Log.v(LOG_TAG, "url = " + url.toString());
                    Log.v(LOG_TAG, "ResponseCode = " + conn.getResponseCode());
                    Log.v(LOG_TAG, "ResponseMessage = " + conn.getResponseMessage());

                    // Reading Stream from InputStream
                    inputStream = conn.getInputStream();
                    responseJsonObject = readFromStream(inputStream);
                    String  tokenType = responseJsonObject.getString(getString(R.string.pref_access_token_key));
                    String expiresIn = responseJsonObject.getString(getString(R.string.pref_expires_in_key));
                    String accessToken = responseJsonObject.getString(getString(R.string.pref_access_token_key));
                    String refreshToken = responseJsonObject.getString(getString(R.string.pref_refresh_token_key));

//                    Log.v(LOG_TAG, "tokenType =  " + tokenType);
//                    Log.v(LOG_TAG, "accessToken = " + accessToken);
//                    Log.v(LOG_TAG, "expiresIn = " + expiresIn);
//                    Log.v(LOG_TAG, "refreshToken =  " + refreshToken);

                    // Store data In Preference
//                    storeData(responseJsonObject);
                    SharedPreferences pref = getSharedPreferences(UserEntry.TABLE_NAME, 0);
                    SharedPreferences.Editor prefEditor = pref.edit();
                    prefEditor.putString(getString(R.string.pref_username_key), mEmail);
                    prefEditor.putString(getString(R.string.pref_password_key), mPassword);
                    prefEditor.putString(getString(R.string.pref_token_type_key), tokenType);
                    prefEditor.putString(getString(R.string.pref_expires_in_key), expiresIn);
                    prefEditor.putString(getString(R.string.pref_access_token_key), accessToken);
                    prefEditor.putString(getString(R.string.pref_refresh_token_key),refreshToken );
                    prefEditor.commit();


                    /**
                     * Now Getting All User Info
                     */
                    conn = (HttpURLConnection) urlUserInfo.openConnection();
                    conn.setRequestMethod("GET");

                    if (conn.getResponseCode() == 200) {

                        inputStream = conn.getInputStream();

                        // Reading from Stream
                        StringBuilder userInfoString = new StringBuilder();
                        if (inputStream != null) {
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            String line = reader.readLine();
                            while(line != null) {
                                userInfoString.append(line);
                                line = reader.readLine();
                            }
                        }

//                        Log.v(LOG_TAG, "UserInfoString = " + userInfoString.toString());

                        // Extract UserInfo from JSON Object
                        JSONObject userInfoJSON =  User.readUserFromJson(userInfoString.toString(), getBaseContext());

                        Log.v(LOG_TAG, "UserInfoJSON = " + userInfoJSON.toString());


                    } else {

                        Log.v(LOG_TAG, "(ResponseCode , ResponseMessage) = ( " +
                                conn.getResponseCode() + " , " +
                                conn.getResponseMessage()
                        );
                    }


                    return false;
                } else {
                    Log.v(LOG_TAG, "url: " + url.toString());
                    Log.v(LOG_TAG, "ResponseCode: " + conn.getResponseCode());
                    Log.v(LOG_TAG, "ResponseMessage: " + conn.getResponseMessage());


                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (inputStream != null) {
                    // Closing the input stream could throw an IOException, which is why
                    // the makeHttpRequest(URL url) method signature specifies than an IOException
                    // could be thrown.
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }


        private JSONObject readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            JSONObject rootObject = null;

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }

                // makeJsonObjectFromOutputString
                String outputString = output.toString();

                if (TextUtils.isEmpty(outputString)) {
                    return null;
                }

                // Create an empty JSON Object

                try {
                    rootObject = new JSONObject(outputString);

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                    if (inputStream != null) {

                        inputStream.close();
                    }
                }

                return rootObject;

            }

            return null;
        }


    }

    public class OnTokenAcquired implements AccountManagerCallback<Bundle> {


        @Override
        public void run(AccountManagerFuture<Bundle> future) {
            // Get the result of the operation from the AccountManagerFuture.
            try {
                Bundle bundle = future.getResult();

                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);

                Log.v(LOG_TAG, "token = " + token);

            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            }
        }
    }
}

