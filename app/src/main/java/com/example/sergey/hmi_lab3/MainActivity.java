package com.example.sergey.hmi_lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sergey.hmi_lab3.Admin.AdminActivity;
import com.example.sergey.hmi_lab3.AdminMenu.AdminMenuActivity;
import com.example.sergey.hmi_lab3.User.UserActivity;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.db.model.User;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RuntimeExceptionDao<User, Long> usersDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Stetho.initializeWithDefaults(this);
        HelperFactory.setHelper(getApplicationContext());

        databaseHelper = HelperFactory.getHelper();
        setTestData();
    }

    private void setTestData() {
        ConnectionSource connectionSource = databaseHelper.getConnectionSource();
        try {
            TableUtils.createTableIfNotExists(connectionSource, Order.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        usersDao = databaseHelper.getUsersDao();
        RuntimeExceptionDao<Item, Long> itemsDao = databaseHelper.getItemsDao();
        if (usersDao.queryForAll().size() == 0){
            User user = new User();
            user.setName("Dave");
            user.setOrders(new ArrayList<>());
            usersDao.create(user);
        }
        if (itemsDao.queryForAll().size() < 7){
            ArrayList<Item> items = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.setAvailable(true);
                item.setName("Item " + i + 2);
                item.setNumber(i + 3);
                items.add(item);
            }
            itemsDao.create(items);
        }
    }

    @OnClick(R.id.user)
    void gotoUser() {
        Intent intent = UserActivity.getIntent(this);
        startActivity(intent);
    }

    @OnClick(R.id.admin)
    void gotoAdmin() {
        Intent intent = AdminMenuActivity.getIntent(this);
        startActivity(intent);
    }
}
