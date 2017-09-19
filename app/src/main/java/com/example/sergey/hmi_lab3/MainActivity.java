package com.example.sergey.hmi_lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sergey.hmi_lab3.Admin.AdminActivity;
import com.example.sergey.hmi_lab3.User.UserActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.user)
    void gotoUser(){
        Intent intent = UserActivity.getIntent(this);
        startActivity(intent);
    }
    @OnClick(R.id.admin)
    void gotoAdmin(){
        Intent intent = AdminActivity.getIntent(this);
        startActivity(intent);
    }
}
