package com.example.sergey.hmi_lab3.AdminItems;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergey.hmi_lab3.Admin.EditOrderDialogFragment;
import com.example.sergey.hmi_lab3.R;
import com.example.sergey.hmi_lab3.db.helper.DatabaseHelper;
import com.example.sergey.hmi_lab3.db.model.Item;
import com.example.sergey.hmi_lab3.utils.HelperFactory;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddItemDialogFragment extends DialogFragment {


    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.is_available)
    CheckBox isAvailable;

    private Item item;

    private RuntimeExceptionDao<Item, Long> itemsDao;
    private EditOrderDialogFragment.OnDismissListener onDismissListener;

    static AddItemDialogFragment newInstance(@Nullable Item item) {
        AddItemDialogFragment f = new AddItemDialogFragment();
        if (item != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            f.setArguments(bundle);
        }
        return f;
    }

    @OnClick(R.id.save)
    void save() {
        if (name.getText().length() == 0) {
            Toast.makeText(getContext(), "Name field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number.getText().length() == 0) {
            Toast.makeText(getContext(), "Number field is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (item != null){
            item.setNumber(Integer.parseInt(number.getText().toString()));
            item.setName(name.getText().toString());
            item.setAvailable(isAvailable.isChecked());
            itemsDao.update(item);
        }else {
            Item item = new Item();
            item.setNumber(Integer.parseInt(number.getText().toString()));
            item.setName(name.getText().toString());
            item.setAvailable(isAvailable.isChecked());
            itemsDao.create(item);
        }
        this.dismiss();
    }

    public void setOnDismissListener(EditOrderDialogFragment.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDismissListener.onDismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_item_dialog, container, false);

        ButterKnife.bind(this, v);

        HelperFactory.setHelper(getContext());
        DatabaseHelper helper = HelperFactory.getHelper();
        itemsDao = helper.getItemsDao();
        Bundle arguments = getArguments();
        if (arguments != null) {
            item = (Item) arguments.getSerializable("item");
            name.setText(item.getName());
            number.setText(String.valueOf(item.getNumber()));
            isAvailable.setChecked(item.getAvailable());
        }

        return v;
    }


    public interface OnDismissListener {
        void onDismiss();
    }
}
