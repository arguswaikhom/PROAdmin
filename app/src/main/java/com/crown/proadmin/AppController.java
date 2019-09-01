package com.crown.proadmin;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AppController extends Application {

    public static final String TAG_CREDENTIAL_SP = "login_user_credential";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_PROFILE_PICTURE_URL = "profile_picture_url";
    public static final String TAG_ACCOUNT_TYPE = "account_type";

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void saveLoginUserCredentials(String username, String password, String email, String profile_picture_url, String accountType) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(TAG_USERNAME, username);
        editor.putString(TAG_PASSWORD, password);
        editor.putString(TAG_EMAIL, email);
        editor.putString(TAG_PROFILE_PICTURE_URL, profile_picture_url);
        editor.putString(TAG_ACCOUNT_TYPE, accountType);
        editor.apply();
    }

    public boolean isUserLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);
        if (sharedPreferences != null) {
            String username = sharedPreferences.getString(TAG_USERNAME, null);
            String password = sharedPreferences.getString(TAG_USERNAME, null);
            String email = sharedPreferences.getString(TAG_USERNAME, null);
            String profile_picture_url = sharedPreferences.getString(TAG_USERNAME, null);
            String account_type = sharedPreferences.getString(TAG_USERNAME, null);

            if ((username == null || password == null || email == null || profile_picture_url == null || account_type == null)) {
                return false;
            }
            return (!username.equals("") && !password.equals("") && !email.equals("") && !profile_picture_url.equals("") && !account_type.equals(""));
        }
        return true;
    }

    public void logout() {
        mSharedPreferences.edit().clear().apply();
    }

    public String getLoginUserUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);
        //return "bbake";
        return sharedPreferences.getString(TAG_USERNAME, null);
    }

    public String getLoginUserPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);
        //return "bbake";
        return sharedPreferences.getString(TAG_PASSWORD, null);
    }

    public String getLoginUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);
        return sharedPreferences.getString(TAG_EMAIL, null);
    }

    public String getLoginUserProfilePictureUrl() {
        SharedPreferences sharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);
        return sharedPreferences.getString(TAG_PROFILE_PICTURE_URL, null);
    }

    public String getLoginUserAccountType() {
        SharedPreferences sharedPreferences = getSharedPreferences(TAG_CREDENTIAL_SP, MODE_PRIVATE);
        return sharedPreferences.getString(TAG_ACCOUNT_TYPE, null);
    }

    public String getLoginCredential() {
        String loginCredential = getLoginUserUsername() + ":" + getLoginUserPassword();
        return Base64.encodeToString(loginCredential.getBytes(), Base64.NO_WRAP);
    }

    public Map<String, String> getLoginCredentialHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getLoginCredential());
        return headers;
    }

    public boolean isSuperUserAccount() {
        return getLoginUserAccountType().equals("superuser");
    }

    public static String getDomainUrl() {
        return "http://reachout.pythonanywhere.com";
    }
}
