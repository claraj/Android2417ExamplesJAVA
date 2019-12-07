package com.clara.hydrationroomembeddedrelation.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Person.class,
        parentColumns = "id",
        childColumns = "personId",
        onDelete = CASCADE))
public class Record {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int glassesDrunk;

    private String isoDate;   // ISO date format example "2019-02-05" for 5th February 2019

    int personId;

    public Record() {}

    @Ignore
    public Record(int glassesDrunk, String dateString, int personId) {
        this.glassesDrunk = glassesDrunk;
        this.isoDate = dateString;
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", glassesDrunk=" + glassesDrunk +
                ", date='" + isoDate + '\'' +
                ", personId=" + personId +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGlassesDrunk() {
        return glassesDrunk;
    }

    public void setGlassesDrunk(int glassesDrunk) {
        this.glassesDrunk = glassesDrunk;
    }

    public String getIsoDate() {
        return isoDate;
    }

    public void setIsoDate(String isoDate) {
        this.isoDate = isoDate;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
