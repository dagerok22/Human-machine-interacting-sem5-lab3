package com.example.sergey.hmi_lab3.AdminItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.sergey.hmi_lab3.Admin.EditOrderDialogFragment;
import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.User.ItemListRecyclerAdapter;
import com.example.sergey.hmi_lab3.User.UserActivity;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.helper.ItemClickSupport;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sergey on 22.09.2017.
 */

public class AdminItems extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    EditText search_view;

    private DatabaseHelper databaseHelper;
    private RuntimeExceptionDao<Item, Long> itemsDao;
    private List<Item> dataSet;
    private ItemListRecyclerAdapter itemsRecyclerAdapter;
    private List<Item> items;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminItems.class);
        return intent;
    }

    @OnClick(R.id.fab)
    void addItem(){
        showAddIemDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_items);

        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        HelperFactory.setHelper(this);
        databaseHelper = HelperFactory.getHelper();
        itemsDao = databaseHelper.getItemsDao();

        dataSet = getDataSet();
        setupAdapter();
        setUpSearchView();
    }

    void setupAdapter() {
        itemsRecyclerAdapter = new ItemListRecyclerAdapter(dataSet);
        recyclerView.setAdapter(itemsRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {
        });
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

    private List<Item> getDataSet() {
        items = itemsDao.queryForAll();
        return items;
    }

    private void showAddIemDialog() {
        AddItemDialogFragment dialogFragment = AddItemDialogFragment.newInstance();
        dialogFragment.setOnDismissListener(() -> {
            dataSet = getDataSet();
            itemsRecyclerAdapter.notifyDataSetChanged();
        });
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        dialogFragment.show(supportFragmentManager, "add_item_dialog_fragment");
    }
}
