package com.clara.basichydration;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/* Manages the connection from the app to the datastore
Can talk to other Entities here, or to an API, or some other data store.
App shouldn't care where data comes from.
 */

public class WaterRepository {

    private WaterDAO waterDAO;

    public WaterRepository(Application application) {
        WaterDatabase db = WaterDatabase.getDatabase(application);
        waterDAO = db.waterDAO();
    }

    public void insert(WaterRecord record) {
        // insert record asynchronously (in the background)
        new InsertWaterAsync(waterDAO).execute(record);
    }

    // Database tasks must run the background, not on the UI thread
    static class InsertWaterAsync extends AsyncTask<WaterRecord, Void, Void> {

        private WaterDAO waterDAO;

        InsertWaterAsync(WaterDAO waterDAO) {
            this.waterDAO = waterDAO;
        }

        @Override
        protected Void doInBackground(WaterRecord... waterRecords) {
            waterDAO.insert(waterRecords);
            return null;
        }
    }

    public void update(WaterRecord record) {
        // update record asynchronously (in the background)
        new UpdateWaterAsync(waterDAO).execute(record);
    }

    static class UpdateWaterAsync extends AsyncTask<WaterRecord, Void, Void> {

        private WaterDAO waterDAO;

        UpdateWaterAsync(WaterDAO waterDAO) {
            this.waterDAO = waterDAO;
        }

        @Override
        protected Void doInBackground(WaterRecord... waterRecords) {
            waterDAO.update(waterRecords);
            return null;
        }
    }

    public LiveData<List<WaterRecord>> getAllRecords() {
        return waterDAO.getAllRecords();
    }

    public LiveData<WaterRecord> getRecordForDay(String day) {
        return waterDAO.getRecordForDay(day);
    }


}



