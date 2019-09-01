package com.crown.proadmin.AllUsers;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crown.proadmin.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class UserDetailListAdapter extends ArrayAdapter<UserDetails> {

    public UserDetailListAdapter(Context context, int resource, List<UserDetails> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.au_user_details_row_item, parent, false);
        }

        UserDetails userDetails = getItem(position);

        ImageView profilePictureIV = convertView.findViewById(R.id.iv_audri_profile_thumbnail);
        TextView userNameTV = convertView.findViewById(R.id.tv_audri_username);
        TextView accountTypeTV = convertView.findViewById(R.id.tv_audri_account_type);

        userNameTV.setText(userDetails.getUserName());
        accountTypeTV.setText(Html.fromHtml("Account: " + "<i>" + userDetails.getAccountType() + "</i>"));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_person_gray_24dp);
        requestOptions.error(R.drawable.ic_person_gray_24dp);
        requestOptions.centerCrop();
        requestOptions.circleCrop();

        Glide.with(getContext())
                .load(userDetails.getProfilePictureUrl())
                .apply(requestOptions)
                .into(profilePictureIV);

        return convertView;
    }
}
