package com.cghilardi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

public class CurrencyQuotation {
    
    private TaxStorage taxStorage;
    private static SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final Integer SCALE = 2;
    
    public CurrencyQuotation(TaxStorage taxStorage) {
        this.taxStorage = taxStorage;
    }

    public BigDecimal currencyQuotation(String from, String to, Number value, String quotation) {
        BigDecimal amount = new BigDecimal(value.toString());
        if(amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("value should be > 0");
        
        quotation = format(quotation);
        BigDecimal from_tax = taxStorage.get(quotation, from);
        BigDecimal to_tax = taxStorage.get(quotation, to);
        return amount.multiply(from_tax).divide(to_tax, SCALE, RoundingMode.HALF_UP);
    }

    public static String format(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(inputDateFormat.parse(date));
        } catch(ParseException e) {
            e.printStackTrace();
        }
        cal = CalendarAdjustment.ignoreWeekend(cal);
        return outputDateFormat.format(cal.getTime());
    }

}
