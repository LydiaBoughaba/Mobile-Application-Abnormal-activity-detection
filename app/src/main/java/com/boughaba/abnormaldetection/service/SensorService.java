package com.boughaba.abnormaldetection.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.boughaba.abnormaldetection.alarm.AlarmActivity;
import com.boughaba.abnormaldetection.model.AccelerometerData;
import com.boughaba.abnormaldetection.utils.Constants;
import com.boughaba.abnormaldetection.utils.NotificationUtils;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC;

public class SensorService extends Service {
    private static final long ALARM_DELAY = 0;
    private TensorFlowInferenceInterface tf;
    //private Handler mHandler;
    private static final String TAG = "SensorService";
    // Sensor Params
    private int accelerometerDelay = SensorManager.SENSOR_DELAY_FASTEST;
    // Sensors Setup
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private AccelerometerSensorListener accelerometerSensorListener;
    // Prediction Service
    private PredictionService predictionService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");

        if (intent.getAction().equals(Constants.STOP_FOREGROUND_ACTION)) {
            Log.i(TAG, "Received Stop Foreground Intent");
            sensorManager.unregisterListener(accelerometerSensorListener);
            stopForeground(true);//stop notif
            stopSelf(startId);
        } else {
            // Creating sensor manager
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

            // Creating sensors
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            // Creating prediction service
            predictionService = new PredictionService(getApplicationContext());


            // Creating sensors listeners
            accelerometerSensorListener = new AccelerometerSensorListener();
            // gyroscopeSensorListener = new GyroscopeSensorListener();

            // Showing notification for the foreground service
            startNotification();

            // Adding sensor listeners
            sensorManager.registerListener(accelerometerSensorListener, accelerometer, accelerometerDelay);

        }
        return START_STICKY;
    }

    private void triggerAlarm() {
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_DELAY, pendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(FOREGROUND_SERVICE_TYPE_DATA_SYNC, NotificationUtils.getForegroundNotification(this));
        } else {
            startForeground(FOREGROUND_SERVICE_TYPE_DATA_SYNC, new Notification());
        }
    }

    private void dataCountReached(float[][] accelerometerData) {
        // Pause Receiver
        sensorManager.unregisterListener(accelerometerSensorListener);

        try {
            float[][] result = predictionService.predictAnomaly(accelerometerData);
            Log.e(TAG, "dataCountReached: 00: " + result[0][0] + " 01: " + result[0][1] + " 02: " + result[0][2] + " 03: " + result[0][3]);
            if (result[0][1] > result[0][0] && result[0][1] > result[0][2] && result[0][1] > result[0][3]) {
                triggerAlarm();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Resetting the data to restart the process
        accelerometerSensorListener.resetData();

        // Resume Receiver
        sensorManager.registerListener(accelerometerSensorListener, accelerometer, accelerometerDelay);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class AccelerometerSensorListener implements SensorEventListener {

        final double alpha = 0.8;
        private double[] gravity = new double[3];
        private double[] linear_acceleration = new double[3];
        float[][] accelerometerData = new float[150][3];
        int count = 0;

        void resetData() {
            count = 0;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            accelerometerData[count][0] = sensorEvent.values[0];
            accelerometerData[count][1] = sensorEvent.values[1];
            accelerometerData[count][2] = sensorEvent.values[2];
            count++;
            if (count == 50) {
                dataCountReached(accelerometerData);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}


