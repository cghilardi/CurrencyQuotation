package com.cghilardi;

import java.util.HashMap;
import java.math.BigDecimal;
import java.net.URL;

public interface QuotationDownloader {
    HashMap<String, BigDecimal> getQuotations(URL url);
}
