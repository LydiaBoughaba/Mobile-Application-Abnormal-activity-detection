package com.boughaba.abnormaldetection.utils;

import android.telephony.SmsManager;

public class SmsUtils {
    public static void sendStandardSMS(String phoneNumber, String content){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, content, null, null);
    }
}
