package com.example.sergey.hmi_lab3.db.model;

import com.example.sergey.hmi_lab3.db.DatabaseContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = DatabaseContract.Orders.COLUMN_NAME_TABLE_NAME)
public class Order {

    @DatabaseField(columnName = DatabaseContract.Orders.COLUMN_NAME_ITEM_ID, canBeNull = false)
    private long itemId;

    @DatabaseField(columnName = DatabaseContract.Orders.COLUMN_NAME_USER,
            foreign = true,
            foreignAutoRefresh = true)
    private User customer;

    @DatabaseField(columnName = DatabaseContract.Orders.COLUMN_NAME_DATE)
    private long date;

    @DatabaseField(columnName = DatabaseContract.Orders.COLUMN_NAME_STATUS)
    private int status;

    @DatabaseField(columnName = DatabaseContract.Orders.COLUMN_NAME_ORDER_ID,
            generatedId = true)
    private long orderId;

    public Order() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
