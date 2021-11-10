package com.boughaba.abnormaldetection.ui.alarm;

import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.utils.SharedPrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAlarmActivity extends AppCompatActivity {
    @BindView(R.id.time_alarm_edit)
    NumberPicker time_alarm;

    SharedPrefsUtils sharedPrefsUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        ButterKnife.bind(this);
        disableDarkMode();
        sharedPrefsUtils = SharedPrefsUtils.getInstance(getApplicationContext());
        init();
    }

    void init() {
        time_alarm.setMinValue(5);
        time_alarm.setMaxValue(60);
        time_alarm.setValue(sharedPrefsUtils.getTimer());
    }

    @OnClick(R.id.bt_edit_alarm)
    void onSave() {
        sharedPrefsUtils.saveTimer(time_alarm.getValue());
        EditAlarmActivity.this.finish();
    }

    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
