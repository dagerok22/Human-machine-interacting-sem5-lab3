package com.example.sergey.hmi_lab3.utils;

import java.text.SimpleDateFormat;

/**
 * Created by sergey on 21.09.2017.
 */

public class DateTransformerUtils {

    public static String getFormattedDateFromLong(long date){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String format = dt.format(date);
        return format;
    }

}
