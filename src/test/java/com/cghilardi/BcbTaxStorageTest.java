package com.cghilardi;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.net.URL;
import java.util.HashMap;
import java.math.BigDecimal;

public class BcbTaxStorageTest {

    private BcbTaxStorage taxStorage;
    private QuotationDownloader downloader;
    
    @Before
    public void setUp() {
        downloader = mock(QuotationDownloader.class);
        taxStorage = new BcbTaxStorage(downloader);
        
        HashMap<String, BigDecimal> quotations = new HashMap<>();
        quotations.put("ARS", new BigDecimal(0.25));
        when(downloader.getQuotations(any(URL.class))).thenReturn(quotations);
    }

    @Test
    public void cachesQuotations() {
        taxStorage.get("20160405", "ARS");
        taxStorage.get("20160405", "ARS");
        verify(downloader, times(1)).getQuotations(any(URL.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWithoutProperCurrency() {
        taxStorage.get("20160405", "AAA");
    }

    @Test
    public void returnRightFullPath() {
        String fullPath = "http://www4.bcb.gov.br/Download/fechamento/20160406.csv";
        assertEquals(fullPath, taxStorage.fullPath("20160406").toString());
    }

}
