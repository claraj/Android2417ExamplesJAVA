package com.clara.beemap;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.clara.beemap.db.Bee;
import com.clara.beemap.db.BeeRepository;

import java.util.List;

public class BeeViewModel extends AndroidViewModel {

    private BeeRepository mBeeRepository;
    private LiveData<List<Bee>> mRecentBees;

    public BeeViewModel(@NonNull Application application) {
        super(application);
        mBeeRepository = new BeeRepository(application);
    }

    public LiveData<List<Bee>> getRecentBees(int results) {
        return mBeeRepository.getRecentBees(results);
    }

    public void insert(Bee bee) {
        mBeeRepository.insert(bee);
    }

    public void delete(Bee bee) {
        mBeeRepository.delete(bee);
    }

    public void delete(int id) {
        mBeeRepository.delete(id);
    }
}
