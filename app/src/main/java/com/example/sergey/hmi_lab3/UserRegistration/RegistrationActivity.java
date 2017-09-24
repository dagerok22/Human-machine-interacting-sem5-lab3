package com.example.sergey.hmi_lab3.UserRegistration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergey.hmi_lab3.AdminItems.AdminItems;
import com.example.sergey.hmi_lab3.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.date_of_birth)
    Button dateBtn;
    @BindView(R.id.register)
    Button register;
    private Calendar calendar;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmationPassword;


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.isEmpty();
    }

    boolean isNameValid(String name) {
        if (name.isEmpty()){
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.date_of_birth)
    void enterDateOfBirth() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) ->
                {
                    calendar.set(i, i1, i2);
                    dateBtn.setText(i2 + "." + i1 + "." + i);
                    register.setEnabled(true);
                }
        );
        datePickerDialog.show();
    }


    @OnClick(R.id.register)
    void register() {
        if (!isNameValid(name.getText().toString())){
            Toast.makeText(this, "Name is not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isEmailValid(email.getText().toString())){
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPasswordsSimilar(password.getText().toString(), confirmationPassword.getText().toString())){
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "New user registered", Toast.LENGTH_SHORT).show();
    }

    private boolean isPasswordsSimilar(String s, String s1) {
        return s.equals(s1) && !s.isEmpty();
    }
}
