package com.example.sergey.hmi_lab3.db.model;

import com.example.sergey.hmi_lab3.db.DatabaseContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = DatabaseContract.Items.COLUMN_NAME_TABLE_NAME)
public class Item implements Serializable {

    @DatabaseField(columnName = DatabaseContract.Items.COLUMN_NAME_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = DatabaseContract.Items.COLUMN_NAME_NUMBER, defaultValue = "0")
    private Integer number;

    @DatabaseField(columnName = DatabaseContract.Items.COLUMN_NAME_ID,
            generatedId = true)
    private long contactId;

    @DatabaseField(columnName = DatabaseContract.Items.COLUMN_NAME_IS_AVAILABLE)
    private Boolean isAvailable;


    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
