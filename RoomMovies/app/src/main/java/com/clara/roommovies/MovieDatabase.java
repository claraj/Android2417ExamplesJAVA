package com.clara.roommovies;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*

Threads:

Executing different parts of code at the same time. Android apps have a UI thread,
and slow or long-running processes should run on background threads so the UI remains responsive.

Volatile:

https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for
If we have a volatile variable, it cannot be cached into the computer's(microprocessor) cache memory by any thread.
Access always happened from main memory.

If there is a write operation going on a volatile variable, and suddenly a read operation is requested,
it is guaranteed that the write operation will be finished prior to the read operation.

Synchronized:

Only one thread can run the code in a synchronized block at any time

So, by making the INSTANCE volatile, and the code that modifies it Synchronized, it guarantees
that it will only be created one time, even if two threads try to access it at the same time.

*/

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static volatile MovieDatabase INSTANCE;

    public abstract MovieDAO movieDAO();  // Abstract method

    static MovieDatabase getDatabase(final Context contex) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {   // Only one thread can run this code at one time
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(contex.getApplicationContext(),
                            MovieDatabase.class, "Movie").build();
                }
            }
        }
        return INSTANCE;
    }

}


