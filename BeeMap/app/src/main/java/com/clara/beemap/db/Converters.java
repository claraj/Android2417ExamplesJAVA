package com.clara.beemap.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date dateFromTimestamp(long time) {
        return new Date(time);
    }

    @TypeConverter
    public static long dateToTimestamp(Date date) {
        return date == null ? 0 : date.getTime();
    }
}
