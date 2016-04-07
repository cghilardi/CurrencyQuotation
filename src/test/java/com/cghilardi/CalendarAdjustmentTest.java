package com.cghilardi;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Calendar;

public class CalendarAdjustmentTest {

    private Calendar cal;

    @Before
    public void setUp() {        
        cal = Calendar.getInstance();
    }

    @Test
    public void returnSameCalendarIfWeekDay() {
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal = CalendarAdjustment.ignoreWeekend(cal);
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), Calendar.MONDAY);
    }

    @Test
    public void returnFridayForSaturday() {
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal = CalendarAdjustment.ignoreWeekend(cal);
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), Calendar.FRIDAY);
    }
    
    @Test
    public void returnFridayForSunday() {
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal = CalendarAdjustment.ignoreWeekend(cal);
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), Calendar.FRIDAY);
    }

}
