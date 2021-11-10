package com.boughaba.abnormaldetection.ui.contact;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.model.Contact;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends AppCompatActivity {
    @BindView(R.id.tf_name)
    TextInputLayout name;
    @BindView(R.id.tf_first_name)
    TextInputLayout firstName;
    @BindView(R.id.tf_phone_number)
    TextInputLayout phone;
    ViewModelAddContact viewModelAddContact;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        viewModelAddContact = new ViewModelProvider(this).get(ViewModelAddContact.class);
        disableDarkMode();
    }

    @OnClick(R.id.bt_ok)
    void okClicked(){
        Contact contact = new Contact(name.getEditText().getText().toString(),firstName.getEditText().getText().toString(),
                phone.getEditText().getText().toString());
        viewModelAddContact.insertContact(contact);
        AddContactActivity.this.finish();
    }

    @OnClick(R.id.bt_cancel)
    void cancelClicked() {
        /*ContactFragment fragment = new ContactFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        finish();*/
        AddContactActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
