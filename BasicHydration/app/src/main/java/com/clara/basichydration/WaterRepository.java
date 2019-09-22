package com.clara.basichydration;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;

public class WaterRepository {

    private WaterDAO waterDAO;

    public WaterRepository(Application application) {
        WaterDatabase db = WaterDatabase.getDatabase(application);
        waterDAO = db.waterDAO();
    }

    public void update(WaterRecord record) {
        new UpdateWaterAsync(waterDAO).execute(record);
    }

    public void insert(WaterRecord record) {
        new InsertWaterAsync(waterDAO).execute(record);
    }


    public LiveData<List<WaterRecord>> getAllRecords() {
        return waterDAO.getAllRecords();
    }


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

}
