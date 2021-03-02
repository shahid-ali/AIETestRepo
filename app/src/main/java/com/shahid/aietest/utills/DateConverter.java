package com.shahid.aietest.utills;

import androidx.room.TypeConverter;
import java.util.Date;

// date convert support to/into date and long for easily storing data in sqlite
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
