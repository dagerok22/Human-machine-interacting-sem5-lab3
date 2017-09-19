package com.example.sergey.hmi_lab3.User;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.helper.ItemClickSupport;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    private List<Item> dataSet;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RuntimeExceptionDao<Item, Long> itemsDao;

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, UserActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);


        HelperFactory.setHelper(this);
        DatabaseHelper databaseHelper = HelperFactory.getHelper();
        itemsDao = databaseHelper.getItemsDao();
        setUpTestData();

        dataSet = getDataSet();
        setupAdapter();
    }

    private void setUpTestData() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setAvailable(true);
            item.setName("Item " + i + 1);
            item.setNumber(i + 3);
        }
        itemsDao.create(items);
    }

    private List<Item> getDataSet(){
        List<Item> items = itemsDao.queryForAll();
        return items;
    }

    void setupAdapter(){
        ItemListRecyclerAdapter itemsRecyclerAdapter = new ItemListRecyclerAdapter(dataSet);
        recyclerView.setAdapter(itemsRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {});
    }
}
