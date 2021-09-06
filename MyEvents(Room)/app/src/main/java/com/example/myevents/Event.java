package com.example.myevents;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class Event {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "location")
    String location;

    @ColumnInfo(name = "description")
    String description;

    public Event(String name, String date, String location, String description) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    @Ignore
    public Event(int id, String name, String date, String location, String description ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
