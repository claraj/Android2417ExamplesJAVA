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
        new UpdateWaterAsync().execute(record);
    }

    public void insert(WaterRecord record) {
        new InsertWaterAsync().execute(record);
    }

    public LiveData<WaterRecord> getRecordForDay(String day) {
        return waterDAO.getRecordForDate(day);
    }


    class InsertWaterAsync extends AsyncTask<WaterRecord, Void, Void> {

        @Override
        protected Void doInBackground(WaterRecord... waterRecords) {
            waterDAO.insert(waterRecords);
            return null;
        }
    }


    class UpdateWaterAsync extends AsyncTask<WaterRecord, Void, Void> {

        @Override
        protected Void doInBackground(WaterRecord... waterRecords) {
            waterDAO.update(waterRecords);
            return null;
        }
    }

}
