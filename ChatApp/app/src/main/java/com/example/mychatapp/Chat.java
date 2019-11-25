package com.example.mychatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = new Bundle();
        bundle.putString("current_user",getIntent().getStringExtra("current_user"));
        bundle.putString("other_user",getIntent().getStringExtra("other_user"));

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container3,chatFragment).commit();

    }
}
