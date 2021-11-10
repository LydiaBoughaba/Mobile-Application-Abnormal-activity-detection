package com.boughaba.abnormaldetection.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.boughaba.abnormaldetection.dao.ContactDao;
import com.boughaba.abnormaldetection.dao.UserDao;
import com.boughaba.abnormaldetection.model.Contact;
import com.boughaba.abnormaldetection.model.User;

@Database(entities = {User.class, Contact.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ContactDao contactDao();

    private static AppDataBase INSTANCE;
    private static final String DB_NAME = "health_db";

    public static AppDataBase getDatabase(Context context){
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDataBase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
