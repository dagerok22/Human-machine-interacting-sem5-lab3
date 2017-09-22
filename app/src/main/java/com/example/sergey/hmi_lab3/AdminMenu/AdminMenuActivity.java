package com.example.sergey.hmi_lab3.AdminMenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sergey.hmi_lab3.Admin.AdminActivity;
import com.example.sergey.hmi_lab3.AdminItems.AdminItems;
import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sergey on 22.09.2017.
 */

public class AdminMenuActivity extends AppCompatActivity {

    @OnClick(R.id.to_items)
    void toItems(){
        Intent intent = AdminItems.getIntent(this);
        startActivity(intent);
    }
    @OnClick(R.id.to_orders)
    void toOrders(){
        Intent intent = AdminActivity.getIntent(this);
        startActivity(intent);
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminMenuActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

    }
}
