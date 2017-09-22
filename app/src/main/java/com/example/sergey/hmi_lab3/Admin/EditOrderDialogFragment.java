package com.example.sergey.hmi_lab3.Admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.db.model.Order;
import com.example.sergey.hmi_lab3.utils.DateTransformerUtils;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sergey on 22.09.2017.
 */

public class EditOrderDialogFragment extends DialogFragment {

    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.item)
    TextView itemName;
    @BindView(R.id.status)
    Spinner statusSpinner;
    //    @BindView(R.id.status)
//    EditText status;
    private Order order;
    private RuntimeExceptionDao<Order, Long> ordersDao;
    private OnDismissListener onDismissListener;

    static EditOrderDialogFragment newInstance(Order order) {
        EditOrderDialogFragment f = new EditOrderDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("order", order);
        f.setArguments(args);
        // Supply num input as an argument.
        return f;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener){
        this.onDismissListener = onDismissListener;
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        order = (Order) arguments.getSerializable("order");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDismissListener.onDismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_order_dialog, container, false);

        ButterKnife.bind(this, v);

        HelperFactory.setHelper(getContext());
        DatabaseHelper helper = HelperFactory.getHelper();
        RuntimeExceptionDao<Item, Long> itemsDao = helper.getItemsDao();
        ordersDao = helper.getOrdersDao();

        Item item = itemsDao.queryForId(order.getItemId());
        id.setText(String.valueOf("Order id: " + order.getOrderId()));
        customer.setText("Customer: " + order.getCustomer().getName());
        date.setText("Order date: " + DateTransformerUtils.getFormattedDateFromLong(order.getDate()));
        itemName.setText("Item name: " + item.getName());

        ArrayList<String> statusCodes = new ArrayList<>();
        statusCodes.add("processing");
        statusCodes.add("dispatched");
        statusCodes.add("delivered");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, statusCodes);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        statusSpinner.setAdapter(dataAdapter);
        statusSpinner.setSelection(order.getStatus());
//        status.setText(String.valueOf("Order status: " + order.getStatus()));

        setUpStatusSpinnerListener();

        return v;
    }

    private void setUpStatusSpinnerListener() {
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                order.setStatus((int) l);
                ordersDao.update(order);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
