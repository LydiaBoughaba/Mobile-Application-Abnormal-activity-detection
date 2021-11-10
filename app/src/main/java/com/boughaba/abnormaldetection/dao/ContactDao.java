package com.boughaba.abnormaldetection.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.boughaba.abnormaldetection.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    LiveData<List<Contact>> getAll();

    @Query("DELETE FROM Contact")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Contact... contacts);

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);

}
