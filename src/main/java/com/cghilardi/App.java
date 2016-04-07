package com.cghilardi;

public class App {

    public static void main(String[] args) {
        CurrencyQuotation cq = new CurrencyQuotation(new BcbTaxStorage());
        System.out.println(cq.currencyQuotation("USD", "ARS", 10, "05/04/2016"));
    }
}
