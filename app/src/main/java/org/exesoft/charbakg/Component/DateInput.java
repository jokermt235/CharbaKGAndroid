package org.exesoft.charbakg.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateInput {
    public static String getDefaultFromDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return formatter.format(cal.getTime());
    }
    public static String setDefaultToDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(new Date());
    }

    public static String getFormatedDate(Date date){
        return  new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static Date getFromDateDefault(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }
}
