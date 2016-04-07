package com.cghilardi;

import java.math.BigDecimal;

public interface TaxStorage {
    BigDecimal get(String quotation, String currency);
}
