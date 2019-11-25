package com.example.mychatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UsersList extends AppCompatActivity {
    private String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        currentUser = getIntent().getStringExtra("current_user");

        Bundle bundle = new Bundle();
        bundle.putString("current_user",currentUser);

        UsersListFragment usersListFragment = new UsersListFragment();
        usersListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container2,usersListFragment).commit();


    }


}
