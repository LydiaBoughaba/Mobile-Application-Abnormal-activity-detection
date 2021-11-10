package com.boughaba.abnormaldetection.ui.contact;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.model.Contact;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyContactActivity extends AppCompatActivity {

    @BindView(R.id.tf_name_change)
    TextInputEditText editTextName;
    @BindView(R.id.tf_first_name_change)
    TextInputEditText editTextFirstName;
    @BindView(R.id.tf_phone_number_change)
    TextInputEditText editTextPhoneNumber;
    @BindView(R.id.bt_change)
    Button bt_edit;
    ViewModelChangeContact viewModelChangeContact;

    Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_contact);
        ButterKnife.bind(this);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        viewModelChangeContact = new ViewModelProvider(this).get(ViewModelChangeContact.class);
        initView();
    }

    @OnClick(R.id.bt_change)
    void onChange(){
        contact.setName(editTextName.getText().toString());
        contact.setFirstName(editTextFirstName.getText().toString());
        contact.setPhoneNumber(editTextPhoneNumber.getText().toString());
        viewModelChangeContact.edit(contact);
        finish();
    }

    @OnClick(R.id.bt_no)
    void onCancel(){
        finish();
    }

    public void initView() {
        editTextName.setText(contact.getName());
        editTextFirstName.setText(contact.getFirstName());
        editTextPhoneNumber.setText(contact.getPhoneNumber());
        disableDarkMode();
    }

    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
