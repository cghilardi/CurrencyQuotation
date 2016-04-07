package com.cghilardi;

import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class BcbTaxStorage implements TaxStorage {
    
    private HashMap<String, HashMap<String, BigDecimal>> taxes = new HashMap<>();
    private QuotationDownloader downloader;
    private static final String BASE_PATH = "http://www4.bcb.gov.br/Download/fechamento/";
    private static final String FILE_FORMAT = ".csv";
    
    public BcbTaxStorage(QuotationDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public BigDecimal get(String quotation, String currency) {
        if(taxes.get(quotation) == null) taxes.put(quotation, downloader.getQuotations(fullPath(quotation)));
        if(taxes.get(quotation).get(currency) == null) throw new IllegalArgumentException("Selected quotation does not know currency " + currency);
        return taxes.get(quotation).get(currency);
    }

    public URL fullPath(String quotation) {
        URL url = null;
        try {
            url = new URL(BASE_PATH + quotation + FILE_FORMAT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        return url;
    }
}
