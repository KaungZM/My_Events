package com.example.myevents;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDb extends RoomDatabase {
    public abstract EventDao eventDao();
    private static EventDb eventDb;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static EventDb getDatabase(Context context) {
        if(eventDb == null) {
            synchronized (EventDb.class) {
                if(eventDb == null) {
                    eventDb = Room.databaseBuilder(context.getApplicationContext(), EventDb.class, "event").build();
                }
            }
        }
        return eventDb;
    }
}
