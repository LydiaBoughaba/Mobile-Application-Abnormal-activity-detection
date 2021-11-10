package com.boughaba.abnormaldetection.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.boughaba.abnormaldetection.model.User;
import com.boughaba.abnormaldetection.repository.Repository;

public class SharedPrefsUtils {

    private final static String prefs = "abnormal_prefs";
    private final static String USER_NAME = "name";
    private final static String USER_FIRST_NAME = "first_name";
    private final static String ALARM_TIMER = "alarm_timer";

    private static SharedPrefsUtils INSTANCE;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPrefsUtils(SharedPreferences preferences) {
        this.preferences = preferences;
        this.editor = preferences.edit();
    }

    public static synchronized SharedPrefsUtils getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPrefsUtils(context.getSharedPreferences(prefs, Context.MODE_PRIVATE));
        }
        return INSTANCE;
    }

    public void saveUser(User user) {
        editor.putString(USER_NAME, user.getName()).commit();
        editor.putString(USER_FIRST_NAME,user.getFirstName()).commit();
    }

    public void saveTimer(int timer){
        editor.putInt(ALARM_TIMER, timer).commit();
    }

    public int getTimer(){
        return preferences.getInt(ALARM_TIMER, 5);
    }

    private void deleteUser() {
        editor.remove(USER_NAME).commit();
        editor.remove(USER_FIRST_NAME).commit();
    }

    public User getUser() {
        User user = null;
        if(preferences.contains(USER_NAME) && preferences.contains(USER_FIRST_NAME)){
            user = new User();
            user.setName(preferences.getString(USER_NAME, null));
            user.setFirstName(preferences.getString(USER_FIRST_NAME, null));
        }
        return user;
    }

}
