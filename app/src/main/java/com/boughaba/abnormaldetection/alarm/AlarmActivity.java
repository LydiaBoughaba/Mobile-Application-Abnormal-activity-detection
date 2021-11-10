package com.boughaba.abnormaldetection.alarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.model.Contact;
import com.boughaba.abnormaldetection.model.User;
import com.boughaba.abnormaldetection.repository.Repository;
import com.boughaba.abnormaldetection.service.LocationService;
import com.boughaba.abnormaldetection.utils.LocationUtils;
import com.boughaba.abnormaldetection.utils.SharedPrefsUtils;
import com.boughaba.abnormaldetection.utils.SmsUtils;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = "AlarmActivity";
    private Repository repository;

    @BindView(R.id.swipe_btn)
    SwipeButton swipeButtonAlert;

    @BindView(R.id.tv_timer_min)
    TextView textViewTimerMin;

    @BindView(R.id.tv_timer_sec)
    TextView textViewTimerSec;

    GestureDetector gestureDetector;

    // Count Down Timer
    private CountDownTimer timer;
    private long countMilliseconds;
    private final long countInterval = 1000; // 1 second

    SharedPrefsUtils sharedPrefsUtils;
    MediaPlayer mp;


    // Location Service
    LocationService service;

    DecimalFormat formatter = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        disableDarkMode();
        service = new LocationService(this);
        sharedPrefsUtils = SharedPrefsUtils.getInstance(getApplicationContext());
        countMilliseconds = sharedPrefsUtils.getTimer() * 1000;
        repository = new Repository(getApplication());
        mp = MediaPlayer.create(this, R.raw.alarm_fast_pitch);
        mp.setLooping(true);
        mp.start();
        startTimer();
        //swipeButtonAlert.setAlarm(mp);
    }

    @Override
    public void onAttachedToWindow() {
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        swipeButtonAlert.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                finish();
            }
        });
    }

    //Disable Dark Mode
    public void disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void startTimer() {

        timer = new CountDownTimer(countMilliseconds, countInterval) {
            public void onTick(long millisUntilFinished) {
                textViewTimerMin.setText("" + (((millisUntilFinished / 1000) / 60) % 60));
                textViewTimerSec.setText(formatter.format(((millisUntilFinished / 1000) % 60)));
            }

            public void onFinish() {
                Location lastKnownLocation = service.getLastKnowLocation();
                User user = sharedPrefsUtils.getUser();
                repository.getAll().observe(AlarmActivity.this, new Observer<List<Contact>>() {
                    @Override
                    public void onChanged(List<Contact> contacts) {
                        for (Contact contact : contacts) {
                            SmsUtils.sendStandardSMS(contact.getPhoneNumber(), getMessage(lastKnownLocation, user));
                        }
                        AlarmActivity.this.finish();
                    }
                });
            }
        }.start();
    }

    private String getMessage(Location location, User user) {
        StringBuffer smsBody = new StringBuffer();
        smsBody.append("Une activité anormale a été détecté auprès de la personne ");
        smsBody.append(user.getName());
        smsBody.append(" ");
        smsBody.append(user.getFirstName());
        smsBody.append(" elle est au danger sa position de GPS ");
        smsBody.append("http://maps.google.com?q=");
        smsBody.append(location.getLatitude());
        smsBody.append(",");
        smsBody.append(location.getLongitude());
        return smsBody.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        service.cancelLocationRequest();
        mp.stop();
    }
}
