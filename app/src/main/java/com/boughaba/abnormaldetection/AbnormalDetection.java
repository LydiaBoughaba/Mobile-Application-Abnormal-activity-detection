package com.boughaba.abnormaldetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.boughaba.abnormaldetection.ui.register.RegisterUseActivity;
import com.boughaba.abnormaldetection.utils.Constants;
import com.boughaba.abnormaldetection.utils.SharedPrefsUtils;

public class AbnormalDetection extends Activity {

    SharedPrefsUtils sharedPrefsUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefsUtils = SharedPrefsUtils.getInstance(this);
        Intent intent;
        if (sharedPrefsUtils.getUser() == null) {
            intent = new Intent(AbnormalDetection.this, RegisterUseActivity.class);
            intent.putExtra(Constants.FIRST_TIME_REGISTRATION, true);
        } else {
            intent = new Intent(AbnormalDetection.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

}
