package com.cghilardi;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CsvQuotationDownloaderTest {
    private CsvQuotationDownloader downloader;
    private URL url;
    private URL url404;
    
    @Before
    public void setUp() {
        downloader = new CsvQuotationDownloader();
        
        try {
            url = new URL("http://www4.bcb.gov.br/Download/fechamento/20160406.csv");
            url404 = new URL("http://www4.bcb.gov.br/Download/fechamento/2016040.csv");
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void returnQuotationsWhenUrlIsValid() {
        assertEquals(downloader.getQuotations(url).get("ARS"), new BigDecimal(0.25).setScale(2, RoundingMode.HALF_UP));
        assertEquals(downloader.getQuotations(url ).get("USD"), new BigDecimal(3.67).setScale(2, RoundingMode.HALF_UP));
    }

    @Test(expected = IllegalArgumentException.class)
    public void raiseExceptionWhenUrlIsInvalid() {
        downloader.getQuotations(url404);
    }

}
