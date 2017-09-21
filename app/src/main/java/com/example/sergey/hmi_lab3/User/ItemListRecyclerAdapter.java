package com.example.sergey.hmi_lab3.User;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.model.Item;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sergey on 19.09.2017.
 */

public class ItemListRecyclerAdapter extends RecyclerView.Adapter<ItemListRecyclerAdapter.ViewHolder> {
    private List<Item> dataSet;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;

        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            number = v.findViewById(R.id.number);
        }
    }

    public ItemListRecyclerAdapter(List<Item> data) {
        this.dataSet = data;
    }

    @Override
    public ItemListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_item, parent, false);
        return new ItemListRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemListRecyclerAdapter.ViewHolder holder, int position) {
        Item item = dataSet.get(position);
        holder.name.setText(item.getName());
        holder.number.setText(String.valueOf(item.getNumber()));
    }

    public List<Item> getItems() {
        return dataSet;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(final List<Item> newItems) {
        dataSet = newItems;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
