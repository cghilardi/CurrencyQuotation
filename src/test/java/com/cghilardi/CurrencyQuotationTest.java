package com.cghilardi;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;

public class CurrencyQuotationTest {

    private CurrencyQuotation currencyQuotation;
    private String quotation, formattedQuotation, from, to;
    private TaxStorage taxStorage;

    @Before
    public void setUp() {
        quotation = "11/11/2010";
        formattedQuotation = CurrencyQuotation.format(quotation);
        from = "USD";
        to = "ARS";
        taxStorage = mock(TaxStorage.class);
        currencyQuotation = new CurrencyQuotation(taxStorage);
        when(taxStorage.get(formattedQuotation, from)).thenReturn(new BigDecimal(4));
        when(taxStorage.get(formattedQuotation, to)).thenReturn(new BigDecimal(0.25));
    }

    @Test
    public void convertToTheProperAmount() {
        assertEquals(new BigDecimal(160).setScale(2), currencyQuotation.currencyQuotation(from, to, 10, quotation));
    }

    @Test
    public void formatTheQuotationAccordingly() {
        assertEquals("20160405", CurrencyQuotation.format("05/04/2016"));
    }

}
