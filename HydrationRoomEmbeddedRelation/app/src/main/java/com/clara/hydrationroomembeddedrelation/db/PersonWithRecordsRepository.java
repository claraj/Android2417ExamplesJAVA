package com.clara.hydrationroomembeddedrelation.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class PersonWithRecordsRepository {

    private PersonWithRecordsDao dao;

    public PersonWithRecordsRepository(Application application) {
        HydrationDatabase db = HydrationDatabase.getDatabase(application);
        dao = db.personWithRecordsDao();
    }

    public LiveData<PersonWithRecords> getPersonWithRecordsByName(String name) {
        return dao.getPersonWithRecords(name);
    }


//    public void insert(PersonWithRecords... person) {
//        new InsertPersonAsync(dao).execute(person);
//    }

//    static class InsertPersonAsync extends AsyncTask<PersonWithRecords, Void, Void> {
//        private PersonWithRecordsDao dao;
//
//        InsertPersonAsync(PersonWithRecordsDao dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(PersonWithRecords... persons) {
//            dao.insert(persons);
//            return null;
//        }
//    }


}
