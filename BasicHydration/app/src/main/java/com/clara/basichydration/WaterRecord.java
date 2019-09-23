package com.clara.basichydration;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WaterRecord {

    @NonNull
    @PrimaryKey
    private String day;

    private int glasses;

    public WaterRecord(@NonNull String day, int glasses) {
        this.day = day;
        this.glasses = glasses;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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
                ", day=" + day + '}';
    }
}

