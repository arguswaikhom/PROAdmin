package com.crown.proadmin.CreateUser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crown.proadmin.AppController;
import com.crown.proadmin.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.crown.proadmin.GeneralStatics;

import java.util.HashMap;
import java.util.Map;

import static com.crown.proadmin.AppController.getDomainUrl;

public class CreateUser extends AppCompatActivity {

    private final String TAG = CreateUser.class.getSimpleName();

    private EditText mUsernameET;
    private EditText mEmailET;
    private EditText mPasswordET;
    private EditText mConfirmPasswordET;

    private TextView mNoticeTV;

    private RadioGroup mRadioGroup;

    private Button mCreateUserBtn;

    private ProgressBar mProgressBarBp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu_activity_create_user);

        mUsernameET = findViewById(R.id.et_cacu_username);
        mEmailET = findViewById(R.id.et_cacu_email);
        mPasswordET = findViewById(R.id.et_cacu_password);
        mConfirmPasswordET = findViewById(R.id.et_cacu_confirm_password);

        mNoticeTV = findViewById(R.id.tv_cacu_notice);

        mProgressBarBp = findViewById(R.id.pb_cacu_progress_bar);

        mCreateUserBtn = findViewById(R.id.btn_cacu_create_user);

        mRadioGroup = findViewById(R.id.rg_cacu_account_type);

        mCreateUserBtn.setOnClickListener(this::onClickedCreateUser);
    }

    private void showNotice(String string) {
        mNoticeTV.setVisibility(View.VISIBLE);
        mNoticeTV.setText("\t" + string);
    }

    private void onClickedCreateUser(View view) {

        String username = mUsernameET.getText().toString().trim();
        String email = mEmailET.getText().toString().trim();
        String password = mPasswordET.getText().toString().trim();
        String confirmPassword = mConfirmPasswordET.getText().toString();

        int checkedTeam = mRadioGroup.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(checkedTeam);

        if (username.equals("")) {
            GeneralStatics.showKeyBoard(mUsernameET);
            return;
        }

        if (password.equals("")) {
            GeneralStatics.showKeyBoard(mPasswordET);
            return;
        }

        if (confirmPassword.equals("")) {
            GeneralStatics.showKeyBoard(mConfirmPasswordET);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showNotice("Password doesn't match!!");
            return;
        }

        if (radioButton == null) {
            showNotice("Pick an account type...");
            return;
        }

        GeneralStatics.hideKeyBoard(this);
        mNoticeTV.setVisibility(View.GONE);

        mProgressBarBp.setVisibility(View.VISIBLE);

        String account;

        if (radioButton.getText().equals(getString(R.string.title_account_superuser))) {
            account = "superuser";
        } else {
            account = "staff";
        }

        String url = getDomainUrl() + "/add_user/";

        Map<String, String> param = new HashMap<>();
        param.put("user_name", username);
        param.put("email", email);
        param.put("password", password);
        param.put("account_type", account);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response ->
        {
            mProgressBarBp.setVisibility(View.GONE);
            showNotice(response);
            if (response != null) {
                if (response.equals("200")) {
                    Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.equals("409")) {
                    showNotice("username already exist!!");
                }
            }
        }, error -> {
            mProgressBarBp.setVisibility(View.GONE);
            String regex = "com.android.volley.";
            showNotice(error.toString().replaceFirst(regex, ""));
            Log.v(TAG, error.toString());
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }
        };


        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
