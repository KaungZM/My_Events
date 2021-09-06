package com.example.myevents;

import android.app.DownloadManager;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Insert
    void add(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event")
    void deleteAll();

//    @Query("UPDATE event SET NAME =:name, DATE =:date, LOCATION =:location, DESCRIPTION =:description WHERE ID =:id")
//    void updateQuery(int id, String name, String date, String location, String description);

    @Update
    void update(Event... event);

    @Query("SELECT * FROM event WHERE ID = :id")
    Event loadPersonDataByID(int id);
}
