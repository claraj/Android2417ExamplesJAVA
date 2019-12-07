package com.clara.hydrationroomembeddedrelation.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {
        @Index( value = {"id"}),    // an index makes queries faster.
        @Index( value = {"name"}, unique = true)})
public class Person {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    String name;

    public Person() {}

    @Ignore
    public Person(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
