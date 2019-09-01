package com.crown.proadmin.LoginActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crown.proadmin.AppController;
import com.crown.proadmin.MainActivity;
import com.crown.proadmin.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crown.proadmin.GeneralStatics;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserNameET;
    private EditText mPassWordET;

    private Button mLoginIBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.la_activity_login);

        login();

        mUserNameET = findViewById(R.id.et_al_username);
        mPassWordET = findViewById(R.id.et_al_password);
        mLoginIBtn = findViewById(R.id.ib_al_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mLoginIBtn.setOnClickListener(this::onClickedLogin);

    }

    private void onClickedLogin(View view) {
        String username = mUserNameET.getText().toString().trim();
        String password = mPassWordET.getText().toString().trim();

        if (username.equals("")) {
            GeneralStatics.showKeyBoard(mUserNameET);
            return;
        }

        if (password.equals("")) {
            GeneralStatics.showKeyBoard(mPassWordET);
            return;
        }

        uploadLoginContent(username, password);
    }

    private void uploadLoginContent(String username, String password) {

        /*Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.http))
                .encodedAuthority(getString(R.string.localhost) + ":" + getString(R.string.port_no))
                .appendPath("login")
                .appendPath("");

        String url = builder.build().toString();*/

        String url = AppController.getDomainUrl() + "/login/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                (String response) -> {
                    if (response != null) {
                        if (response.trim().equals("500")) {
                            String errorMessage = "Incorrect username or password";
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            newLogin(response, password);
                        }
                    }
                },
                (VolleyError error) -> {
                    String errorMessage = "Something went wrong!!";
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = username + ":" + password;
                String auth = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void newLogin(String response, String password) {
        JSONObject jsonObject = GeneralStatics.JSONParsingObjectFromString(response);

        String username = GeneralStatics.JSONParsingStringFromObject(jsonObject, "username");
        String email = GeneralStatics.JSONParsingStringFromObject(jsonObject, "email");
        String profile_picture_url = AppController.getDomainUrl() + GeneralStatics.JSONParsingStringFromObject(jsonObject, "avatar");
        String accountType = GeneralStatics.JSONParsingStringFromObject(jsonObject, "account_type");

        if (!accountType.equals("superuser")) {
            Toast.makeText(this, "You don't have access to this app", Toast.LENGTH_SHORT).show();
            return;
        }

        AppController.getInstance().saveLoginUserCredentials(username, password, email, profile_picture_url, accountType);

        login();
    }

    private void login() {
        if (AppController.getInstance().isUserLogin()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
