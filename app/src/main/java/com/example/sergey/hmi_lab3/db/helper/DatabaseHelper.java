package com.example.sergey.hmi_lab3.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.db.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "database_iou.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<Item, Long> itemsDao;
    private RuntimeExceptionDao<Order, Long> ordersDao;
    private RuntimeExceptionDao<User, Long> usersDao;

    public DatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private ConnectionSource connectionSource = null;

    @Override
    public ConnectionSource getConnectionSource() {
        if (connectionSource == null) {
            connectionSource = super.getConnectionSource();
        }
        return connectionSource;
    }

    @Override
    public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, Order.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
        // Upgrade DB
    }

    public RuntimeExceptionDao<Item, Long> getItemsDao() {
        if (itemsDao == null) {
            itemsDao = getRuntimeExceptionDao(Item.class);
        }
        return itemsDao;
    }

    public RuntimeExceptionDao<Order, Long> getOrdersDao() {
        if (ordersDao == null) {
            ordersDao = getRuntimeExceptionDao(Order.class);
        }
        return ordersDao;
    }
    public RuntimeExceptionDao<User, Long> getUsersDao() {
        if (usersDao == null) {
            usersDao = getRuntimeExceptionDao(User.class);
        }
        return usersDao;
    }
}
