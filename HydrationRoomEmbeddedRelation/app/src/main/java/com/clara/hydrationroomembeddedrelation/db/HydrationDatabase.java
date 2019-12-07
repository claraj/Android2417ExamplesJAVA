package com.clara.hydrationroomembeddedrelation.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class, Record.class}, version = 1, exportSchema = false)
public abstract class HydrationDatabase extends RoomDatabase {

    private static volatile HydrationDatabase INSTANCE;

    public abstract PersonDao personDao();  // Abstract method
    public abstract RecordDao recordDao();
    public abstract PersonWithRecordsDao personWithRecordsDao();

    static HydrationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HydrationDatabase.class) {   // Only one thread can run this code at once
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HydrationDatabase.class, "Hydration2").build();
                }
            }
        }
        return INSTANCE;
    }

}
