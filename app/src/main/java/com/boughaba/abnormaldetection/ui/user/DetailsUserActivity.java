package com.boughaba.abnormaldetection.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.ui.register.RegisterUseActivity;
import com.boughaba.abnormaldetection.utils.Constants;
import com.boughaba.abnormaldetection.utils.SharedPrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsUserActivity extends AppCompatActivity {
    SharedPrefsUtils sharedPrefsUtils;
    @BindView(R.id.tv_name_user)
    TextView tv_name;
    @BindView(R.id.tv_first_name_user)
    TextView tv_first_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        disableDarkMode();
        sharedPrefsUtils = SharedPrefsUtils.getInstance(getApplicationContext());
        //get User
        tv_name.setText(sharedPrefsUtils.getUser().getName());
        tv_first_name.setText(sharedPrefsUtils.getUser().getFirstName());
    }


    @OnClick(R.id.bt_edit)
    void goToEditActivity(){
        Intent intent = new Intent(this, RegisterUseActivity.class);
        startActivity(intent);
    }


    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
