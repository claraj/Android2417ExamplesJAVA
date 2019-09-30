package com.clara.beemap.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BeeRepository {

    private BeeDAO mBeeDAO;

    public BeeRepository(Application application) {
        BeeDatabase db = BeeDatabase.getDatabase(application);
        mBeeDAO = db.beeDAO();
    }

    public LiveData<List<Bee>> getRecentBees(int results) {
        return mBeeDAO.getRecentBees(results);
    }

    public void insert(Bee bee){
        new InsertBeeAsyncTask(mBeeDAO).execute(bee);
    }

    public void delete(Bee bee) {
        new DeleteBeeAsyncTask(mBeeDAO).execute(bee);
    }

    public void delete(int id) { new DeleteBeeIDAsyncTask(mBeeDAO).execute(id); }

    private static class InsertBeeAsyncTask extends AsyncTask<Bee, Void, Void> {
        BeeDAO dao;

        public InsertBeeAsyncTask(BeeDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Bee... bees) {
            dao.insert(bees[0]);
            return null;
        }
    }


    private static class DeleteBeeAsyncTask extends AsyncTask<Bee, Void, Void> {
        BeeDAO dao;

        public DeleteBeeAsyncTask(BeeDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Bee... bees) {
            dao.delete(bees[0]);
            return null;
        }
    }



    private static class DeleteBeeIDAsyncTask extends AsyncTask<Integer, Void, Void> {

        BeeDAO dao;

        public DeleteBeeIDAsyncTask(BeeDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            dao.delete(id[0]);
            return null;
        }
    }

}
