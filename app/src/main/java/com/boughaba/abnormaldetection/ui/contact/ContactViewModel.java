package com.boughaba.abnormaldetection.ui.contact;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.boughaba.abnormaldetection.model.Contact;
import com.boughaba.abnormaldetection.repository.Repository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private LiveData<List<Contact>> listContact;
    private Repository repository;

    public ContactViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        listContact = repository.getAll();
    }

    public void insertContact(Contact contact) {
        repository.insert(contact);
    }

    public void deleteContact(Contact contact) {
        repository.delete(contact);
    }

    public LiveData<List<Contact>> getListContact() {
        return listContact;
    }
}