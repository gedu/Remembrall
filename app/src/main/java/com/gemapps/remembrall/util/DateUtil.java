package com.gemapps.remembrall.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by edu on 8/2/16.
 */

public class DateUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy", Locale.US);

    public static String formatDate(){
        return formatDate(0);
    }

    public static String formatDate(int daysToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        Date date = new Date(calendar.getTimeInMillis());

        return DATE_FORMAT.format(date);
    }

    public static String formatDateFromTs(long ts){

        return DATE_FORMAT.format(new Date(ts));
    }

    public static String formatDateFromTs(int daysToAdd, long ts){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return  DATE_FORMAT.format(new Date(calendar.getTimeInMillis()));
    }
}
