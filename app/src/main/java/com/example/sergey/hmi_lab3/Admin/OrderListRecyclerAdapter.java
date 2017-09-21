package com.example.sergey.hmi_lab3.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.utils.DateTransformerUtils;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;


public class OrderListRecyclerAdapter extends RecyclerView.Adapter<OrderListRecyclerAdapter.ViewHolder> {
    private final DatabaseHelper helper;
    private final RuntimeExceptionDao<Item, Long> itemsDao;
    private List<Order> dataSet;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView customer;
        TextView date;
        TextView item;

        ViewHolder(View v) {
            super(v);
            item = v.findViewById(R.id.item);
            date = v.findViewById(R.id.date);
            customer = v.findViewById(R.id.customer);
        }
    }

    public OrderListRecyclerAdapter(List<Order> data, Context context) {
        HelperFactory.setHelper(context);
        helper = HelperFactory.getHelper();
        itemsDao = helper.getItemsDao();
        this.dataSet = data;
    }

    @Override
    public OrderListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_item, parent, false);
        return new OrderListRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderListRecyclerAdapter.ViewHolder holder, int position) {
        Order order = dataSet.get(position);
        Item item = itemsDao.queryForId(order.getItemId());
        holder.customer.setText(order.getCustomer().getName());
        holder.item.setText(item.getName());
        holder.date.setText(DateTransformerUtils.getFormattedDateFromLong(order.getDate()));
    }

    public List<Order> getItems() {
        return dataSet;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(final List<Order> newItems) {
        dataSet.clear();
        dataSet.addAll(newItems);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
