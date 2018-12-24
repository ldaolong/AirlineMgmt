package com.darren.machine.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils
{
    public static final String DATE_FORMAT="yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT="HH:mm:ss";
    
    
    /**
     * 
     * @return
     */
    public static String getstr_current_date(){
      return  LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
    
}
