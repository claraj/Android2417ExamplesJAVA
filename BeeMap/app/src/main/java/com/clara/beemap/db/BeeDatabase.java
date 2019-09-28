package com.clara.beemap.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = { Bee.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BeeDatabase extends RoomDatabase {

    private static volatile BeeDatabase INSTANCE;

    public abstract BeeDAO beeDAO();  // Notice this is an abstract method

    static BeeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BeeDatabase.class) {   // Only one thread can run this code at one time to guarantee only one instance of the database is ever created.
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BeeDatabase.class, "Bee")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}


