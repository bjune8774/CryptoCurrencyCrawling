package com.example.june.webcrawling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JeongByungJune on 2018-01-01.
 */

public class CryptoCurrency {
    private List<String> mCurrencyList = new ArrayList<String>();
    private HashMap<String, Double> mCurrencyPrice = new HashMap<String, Double>();

    public CryptoCurrency() {
        mCurrencyList.add("BTC");
        mCurrencyList.add("ETH");
        mCurrencyList.add("ETC");
        mCurrencyList.add("MTL");
        mCurrencyList.add("BCC");
        mCurrencyList.add("SNT");
        mCurrencyList.add("STORJ");
        mCurrencyList.add("OMG");
        mCurrencyList.add("POWR");
    }

    public void setCurrencyPrice(String currency, Double price) {
        mCurrencyPrice.put(currency, price);
    }

    public double getCurrencyPrice(String currencyName) {
        return mCurrencyPrice.get(currencyName);
    }

    public List<String> getCurrencyList() {
        return mCurrencyList;
    }
}
