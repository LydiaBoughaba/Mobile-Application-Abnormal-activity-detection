package com.boughaba.abnormaldetection.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.boughaba.abnormaldetection.dao.ContactDao;
import com.boughaba.abnormaldetection.dao.UserDao;
import com.boughaba.abnormaldetection.database.AppDataBase;
import com.boughaba.abnormaldetection.model.Contact;

import java.util.List;

public class Repository {
    private UserDao userDao;
    private ContactDao contactDao;
    private LiveData<List<Contact>> contactList;

    public Repository(Application application) {
        AppDataBase database = AppDataBase.getDatabase(application);
        userDao = database.userDao();
        contactDao = database.contactDao();
        contactList = contactDao.getAll();
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public void update(Contact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void delete(Contact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteAll() {
        new DeleteAllContactAsyncTask(contactDao).execute();
    }

    public LiveData<List<Contact>> getAll() {
        return contactList;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao dao;

        private InsertContactAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dao.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao dao;

        private UpdateContactAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao dao;

        private DeleteContactAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            dao.delete(contacts[0]);
            return null;
        }
    }

    private static class DeleteAllContactAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao dao;

        private DeleteAllContactAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

}
