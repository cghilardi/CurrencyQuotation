package com.cghilardi;

import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.math.RoundingMode;

public class CsvQuotationDownloader implements QuotationDownloader {
    private static final Integer SCALE = 2;

    @Override
    public HashMap<String, BigDecimal> getQuotations(URL url) {
        verifyURL(url);
        
        String line = "";
        BigDecimal quotation = null;
        HashMap<String, BigDecimal> quotations = new HashMap<>();
        try(InputStreamReader isr = new InputStreamReader(url.openStream()); BufferedReader br = new BufferedReader(isr)) {
                while ((line = br.readLine()) != null) {
                    quotation = new BigDecimal(line.split(";")[4].replace(",", ".")).setScale(SCALE, RoundingMode.HALF_UP);
                    quotations.put(line.split(";")[3], quotation);
                }
            } catch(IOException e) {
            e.printStackTrace();
        }
        
        return quotations;
    }

    private void verifyURL(URL url) {
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            huc.connect();
            if(huc.getResponseCode() == 404) throw new IllegalArgumentException("Can not retrive quotation with URL" + url);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
