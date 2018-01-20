package com.tal.pseudo_share.data;

import android.arch.persistence.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 19/01/2018.
 */

public class MyDateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp!=null?new Date(timestamp):null;
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date==null?null:date.getTime();
    }

    public static String onlyDateString(Date date){
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return dateString;
    }
}
