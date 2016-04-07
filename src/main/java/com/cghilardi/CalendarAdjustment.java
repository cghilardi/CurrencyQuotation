package com.cghilardi;

import java.util.Calendar;

public class CalendarAdjustment {
    
    public static Calendar ignoreWeekend(Calendar cal) {
        switch(cal.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SATURDAY: cal.add(Calendar.DAY_OF_MONTH, -1); return cal;
        case Calendar.SUNDAY: cal.add(Calendar.DAY_OF_MONTH, -2); return cal;
        default: return cal;
        }
    }

}
