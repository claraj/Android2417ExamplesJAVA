package com.clara.hydrationroom.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonRepository {

    private PersonDao dao;

    public PersonRepository(Application application) {
        HydrationDatabase db = HydrationDatabase.getDatabase(application);
        dao = db.personDao();
    }

    public LiveData<List<Person>> getAllPeople() {
        return dao.getAllPeople();
    }

    public LiveData<Person> getPersonByName(String name) {
        return dao.getPersonByName(name);
    }

    public void insert(Person... person) {
        new InsertPersonAsync(dao).execute(person);
    }

    static class InsertPersonAsync extends AsyncTask<Person, Void, Void> {
        private PersonDao dao;

        InsertPersonAsync(PersonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Person... persons) {
            dao.insert(persons);
            return null;
        }
    }


}
