package com.clara.basichydration;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class WaterRecord {

    @NonNull
    @PrimaryKey
    private int id;

    private int glasses;

    private String dayOfWeek;

    public WaterRecord() {
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGlasses() {
        return glasses;
    }

    public void setGlasses(int glasses) {
        this.glasses = glasses;
    }

    @Override
    public String toString() {
        return "WaterRecord{" +
                "glasses=" + glasses +
                ", date=" + dayOfWeek +
                '}';
    }
}
