package com.clara.basichydration;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = { @Index( value = "day", unique = true )})   // Day of the week should be unique
public class WaterRecord {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int glasses;

    @NonNull
    private String day;

    public WaterRecord(@NonNull String day, int glasses) {
        this.day = day;
        this.glasses = glasses;
    }

    public String getDay() {
        return day;
    }

    public void setDayOfWeek(String day) {
        this.day = day;
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
                ", day=" + day +
                '}';
    }
}
