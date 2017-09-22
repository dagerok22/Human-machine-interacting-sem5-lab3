package com.example.sergey.hmi_lab3.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.example.sergey.hmi_lab3.MainActivity;
import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.helper.ItemClickSupport;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.db.model.User;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    private List<Item> dataSet;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    EditText search_view;
    private Dao<Item, Long> itemsDao;
    private DatabaseHelper databaseHelper;
    private RuntimeExceptionDao<Order, Long> ordersDao;
    private RuntimeExceptionDao<User, Long> usersDao;
    private ItemListRecyclerAdapter itemsRecyclerAdapter;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        HelperFactory.setHelper(this);
        databaseHelper = HelperFactory.getHelper();
        itemsDao = databaseHelper.getItemsDao();
        usersDao = databaseHelper.getUsersDao();
        ordersDao = databaseHelper.getOrdersDao();
//        setUpTestData();

        dataSet = getDataSet();
        setupAdapter();
        setUpSearchView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.releaseHelper();
    }

    private void setUpSearchView() {
        search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                charSequence = charSequence.toString().toLowerCase();

                ArrayList<Item> filteredList = new ArrayList<>();

                for (Item item : dataSet) {
                    String name = item.getName().toLowerCase();
                    if (name.contains(charSequence) || charSequence.equals("")) {
                        filteredList.add(item);
                    }
                }
                itemsRecyclerAdapter.setItems(filteredList);
                itemsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setUpTestData() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setAvailable(true);
            item.setName("Item " + i + 2);
            item.setNumber(i + 3);
            items.add(item);
        }
        try {
            itemsDao.create(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Item> getDataSet() {
        List<Item> items = null;
        try {
            items = itemsDao.queryForAll();
            Iterator<Item> iterator = items.iterator();
            while (iterator.hasNext()) {
                Item next = iterator.next();
                if (!next.getAvailable() || next.getNumber() <= 0) {
                    iterator.remove();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    void setupAdapter() {
        itemsRecyclerAdapter = new ItemListRecyclerAdapter(dataSet);
        recyclerView.setAdapter(itemsRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {
            showBuyDialog(dataSet.get(position), position);
        });
    }

    void showBuyDialog(Item item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Buy?")
                .setMessage("Do you want to buy this?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> buyItem(item, position));
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }

    private void buyItem(Item item, int position) {
        User user = usersDao.queryForAll().get(0);
        Order order = new Order();
        order.setDate((new Date()).getTime());
        order.setItemId(item.getId());
        order.setCustomer(user);
        order.setStatus(0);
//        Collection<Order> orders = user.getOrders();
//        orders.add(order);
        item.setNumber(item.getNumber() - 1);
        ordersDao.create(order);
        usersDao.update(user);
        try {
            itemsDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        itemsRecyclerAdapter.notifyItemChanged(position);
    }
}
