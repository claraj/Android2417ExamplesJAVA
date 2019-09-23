package com.clara.basichydration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WaterViewModel extends AndroidViewModel {

    private WaterRepository repository;

    // Common to cache a copy of results of common queries here
    private LiveData<List<WaterRecord>> allRecords;

    public WaterViewModel(@NonNull Application application) {
        super(application);
        repository = new WaterRepository(application);
        allRecords = repository.getAllRecords();
    }

    public LiveData<List<WaterRecord>> getAllRecords() {
        return allRecords;
    }

    public LiveData<WaterRecord> getRecordForDay(String day) {
        return repository.getRecordForDay(day);
    }

    public void insert(WaterRecord record) {
        repository.insert(record); }

    public void update(WaterRecord record) {
        repository.update(record);
    }
}

