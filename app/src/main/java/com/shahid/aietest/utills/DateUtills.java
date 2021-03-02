package com.shahid.aietest.utills;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtills {

    public DateUtills()
    { }

    public String getDateFormat(Date date)
    {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }


    //returning calendar object from here to ensure consistant calendar object in terms parameters like locale etc
    public Calendar getCalendar()
    {
        return Calendar.getInstance();
    }
}
