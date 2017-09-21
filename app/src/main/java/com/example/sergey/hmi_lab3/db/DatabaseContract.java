package com.example.sergey.hmi_lab3.db;

public final class DatabaseContract {
    private DatabaseContract() {

    }

    public static class Items {
        public static final String COLUMN_NAME_TABLE_NAME = "items";
        public static final String COLUMN_NAME_ID = "item_id";
        public static final String COLUMN_NAME_NAME = "item_name";
        public static final String COLUMN_NAME_NUMBER = "item_number";
        public static final String COLUMN_NAME_IS_AVAILABLE= "item_available";
    }

    public static class Orders {
        public static final String COLUMN_NAME_TABLE_NAME = "order";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_ORDER_ID = "order_id";
        public static final String COLUMN_NAME_USER = "customer";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_STATUS = "status";
    }

    public static class Users {
        public static final String COLUMN_NAME_TABLE_NAME = "users";
        public static final String COLUMN_NAME_ITEM_ID = "user_id";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_ORDERS = "user_orders";
    }

}
