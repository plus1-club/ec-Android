package ru.electric.ec.online.data;

import androidx.room.TypeConverter;

import java.util.Date;

class DateConverter {

    @TypeConverter
    static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
