package com.example.june.webcrawling.crawlingasynctask;

import com.example.june.webcrawling.CryptoCurrency;

/**
 * Created by JeongByungJune on 2018-01-02.
 */

public interface AsyncCallback {
    void onFinish(CryptoCurrency currency);
}
