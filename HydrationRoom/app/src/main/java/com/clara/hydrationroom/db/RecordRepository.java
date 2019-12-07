package com.clara.hydrationroom.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecordRepository {

    private RecordDao dao;

    public RecordRepository(Application application) {
        HydrationDatabase db = HydrationDatabase.getDatabase(application);
        dao = db.recordDao();
    }

    public LiveData<List<Record>> getAllRecordsForPerson(int id) {
        return dao.getRecordsForPerson(id);
    }

    public void insert(Record... records) {
        new InsertRecordAsync(dao).execute(records);
    }

    static class InsertRecordAsync extends AsyncTask<Record, Void, Void> {
        private RecordDao dao;

        InsertRecordAsync(RecordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Record... records) {
            dao.insert(records);
            return null;
        }
    }

}
