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
    private static final String BASE_PATH = "http://www4.bcb.gov.br/Download/fechamento/";
    private static final String FILE_FORMAT = ".csv";
    
    @Override
    public BigDecimal get(String quotation, String currency) {
        if(taxes.get(quotation) == null) download(quotation);
        if(taxes.get(quotation).get(currency) == null) throw new IllegalArgumentException("Selected quotation does not know currency " + currency);
        return taxes.get(quotation).get(currency);
    }

    public HashMap<String, HashMap<String, BigDecimal>> getTaxes() {
        return taxes;
    }

    private void download(String quotation) {
        taxes.put(quotation, new HashMap<>());
        String line = "";
        String file = BASE_PATH + quotation + FILE_FORMAT;
        URL csvFile = null;
           
        // Verify that url is well formed
        try {
            csvFile = new URL(file);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        // Verify that HTTP call to url does not return 404
        try {
            HttpURLConnection huc = (HttpURLConnection) csvFile.openConnection();
            huc.setRequestMethod("HEAD");
            huc.connect();
            if(huc.getResponseCode() == 404) throw new IllegalArgumentException("Can not retrive quotation " + quotation);
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Download file
        try(InputStreamReader isr = new InputStreamReader(csvFile.openStream()); BufferedReader br = new BufferedReader(isr)) {
            while ((line = br.readLine()) != null)
                taxes.get(quotation).put(line.split(";")[3], new BigDecimal(line.split(";")[4].replace(",", ".")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
