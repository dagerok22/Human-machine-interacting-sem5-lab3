package com.example.sergey.hmi_lab3.db.model;

import com.example.sergey.hmi_lab3.db.DatabaseContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = DatabaseContract.Orders.COLUMN_NAME_TABLE_NAME)
public class User implements Serializable {
    @DatabaseField(columnName = DatabaseContract.Users.COLUMN_NAME_ITEM_ID, generatedId = true)
    private long id;

    @DatabaseField(columnName = DatabaseContract.Users.COLUMN_NAME_USER_NAME)
    private String name;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
