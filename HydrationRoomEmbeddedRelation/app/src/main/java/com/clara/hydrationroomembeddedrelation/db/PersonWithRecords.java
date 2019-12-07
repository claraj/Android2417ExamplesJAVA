package com.clara.hydrationroomembeddedrelation.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class PersonWithRecords {

    @Embedded
    public Person person;

    @Relation(parentColumn = "id", entityColumn = "personId")
    public List<Record> records;

    @Override
    public String toString() {
        return "PersonWithRecords{" +
                "person=" + person +
                ", records=" + records +
                '}';
    }
}
