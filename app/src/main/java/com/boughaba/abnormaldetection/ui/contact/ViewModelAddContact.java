package com.boughaba.abnormaldetection.ui.contact;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.boughaba.abnormaldetection.model.Contact;
import com.boughaba.abnormaldetection.repository.Repository;

public class ViewModelAddContact extends AndroidViewModel {
    private Repository repository;

    public ViewModelAddContact(Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insertContact(Contact contact){
        repository.insert(contact);
    }
}
