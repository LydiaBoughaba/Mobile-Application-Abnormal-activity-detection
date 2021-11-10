package com.boughaba.abnormaldetection.ui.user;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.boughaba.abnormaldetection.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserActivity extends AppCompatActivity {
    @BindView(R.id.tf_user_name_edit)
    TextInputEditText editTextName;
    @BindView(R.id.tf_user_first_name_edit)
    TextInputEditText editTextFirstName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        initView();
    }

    void initView(){
        editTextFirstName.setText("Lydia");
        editTextName.setText("Boughaba");
    }

    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}
