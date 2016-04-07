package com.cghilardi;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BcbTaxStorageTest {

    private BcbTaxStorage taxStorage;
    
    @Before
    public void setUp() {
        taxStorage = new BcbTaxStorage();
    }

    @Test
    public void downloadQuotationWithProperArquments() {
        taxStorage.get("20160405", "ARS");
        assertThat(taxStorage.getTaxes().keySet(), hasItem("20160405"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWithoutProperCurrency() {
        taxStorage.get("20160405", "AAA");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void failsWithoutProperQuotation() {
        taxStorage.get("2016040", "ARS");
    }

}
