package com.crown.proadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crown.proadmin.AllUsers.AllUsers;
import com.crown.proadmin.CreateUser.CreateUser;
import com.crown.proadmin.LoginActivity.LoginActivity;
import com.crown.proadmin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createUserBtn = findViewById(R.id.btn_am_create_user);
        Button allUsersBtn = findViewById(R.id.btn_am_all_users);

        createUserBtn.setOnClickListener(this::onClickedCreateUser);
        allUsersBtn.setOnClickListener(this::onClickedAllUsers);
    }

    private void onClickedCreateUser(View view) {
        startActivity(new Intent(this, CreateUser.class));
    }

    private void onClickedAllUsers(View view) {
        startActivity(new Intent(this, AllUsers.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mm_logout) {
            AppController.getInstance().logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
