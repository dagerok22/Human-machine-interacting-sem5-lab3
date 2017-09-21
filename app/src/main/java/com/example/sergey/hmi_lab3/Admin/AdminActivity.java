package com.example.sergey.hmi_lab3.Admin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.User.ItemListRecyclerAdapter;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.helper.ItemClickSupport;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<Order> dataSet;
    private OrderListRecyclerAdapter orderRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private RuntimeExceptionDao<Order, Long> ordersDao;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        HelperFactory.setHelper(this);
        databaseHelper = HelperFactory.getHelper();
        ordersDao = databaseHelper.getOrdersDao();

        dataSet = getDataSet();
        setupAdapter();
    }


    private List<Order> getDataSet() {
        List<Order> orders = ordersDao.queryForAll();
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order next = iterator.next();
            if (next.getStatus() == 2) {
                iterator.remove();
            }
        }
        return orders;
    }

    void setupAdapter() {
        orderRecyclerAdapter = new OrderListRecyclerAdapter(dataSet, this);
        recyclerView.setAdapter(orderRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {

        });
    }

}
