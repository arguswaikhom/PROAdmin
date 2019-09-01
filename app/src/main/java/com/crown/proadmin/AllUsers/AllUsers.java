package com.crown.proadmin.AllUsers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.crown.proadmin.AppController;
import com.crown.proadmin.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.crown.proadmin.AppController.getDomainUrl;
import static com.crown.proadmin.GeneralStatics.JSONParsingArrayFromString;
import static com.crown.proadmin.GeneralStatics.JSONParsingObjectFromArray;
import static com.crown.proadmin.GeneralStatics.JSONParsingStringFromObject;

public class AllUsers extends AppCompatActivity {

    private List<UserDetails> mUserDetailsList;
    private UserDetailListAdapter mUserDetailListAdapter;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.au_activity_all_users);

        mListView = findViewById(R.id.lv_aaau_list_view);

        mUserDetailsList = new ArrayList<>();
        mUserDetailListAdapter = new UserDetailListAdapter(this, R.layout.au_user_details_row_item, mUserDetailsList);

        mListView.setAdapter(mUserDetailListAdapter);

        fetchData();
    }

    private void fetchData() {
        String url = getDomainUrl() + "/all_users/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String output) {
                if (output != null) {
                    parseJsonFeed(output);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    class ShortByName implements Comparator<UserDetails> {

        @Override
        public int compare(UserDetails user1, UserDetails user2) {
            return user1.getUserName().compareToIgnoreCase(user2.getUserName());
        }
    }

    private void parseJsonFeed(String output) {
        JSONArray jsonArray = JSONParsingArrayFromString(output);

        List<UserDetails> userDetailsList = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = JSONParsingObjectFromArray(jsonArray, i);

            String id = JSONParsingStringFromObject(jsonObject, "id");
            String username = JSONParsingStringFromObject(jsonObject, "username");
            String profilePictureUrl = getDomainUrl() + JSONParsingStringFromObject(jsonObject, "image");
            String accountType =  JSONParsingStringFromObject(jsonObject, "account_type");

            UserDetails userDetails = new UserDetails(id, username, accountType, profilePictureUrl);

            userDetailsList.add(userDetails);
        }

        Collections.sort(userDetailsList, new ShortByName());

        mUserDetailListAdapter.addAll(userDetailsList);
        mUserDetailListAdapter.notifyDataSetChanged();
    }
}
