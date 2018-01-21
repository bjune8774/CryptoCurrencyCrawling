package com.example.june.webcrawling.crawlingasynctask;

import android.os.AsyncTask;

import com.example.june.webcrawling.CryptoCurrency;

/**
 * Created by bjune.jeong on 2018-01-03.
 */

public abstract class CrawlingAsyncTaskBase extends AsyncTask<Void, Void, Void> {
    private final String TAG = this.getClass().getName();
    protected final String BTC_MARKET = "BTC";
    protected final String USDT_MARKET = "USDT";
    protected final String KRW_MARKET = "KRW";

    protected final int SLEEP_TIME = 1000;

    private CryptoCurrency mCryptoCurrency;

    private AsyncCallback mCallback;
    private boolean mIsRunning;

    protected CrawlingAsyncTaskBase(AsyncCallback cb) {
        mCallback = cb;
        mIsRunning = true;
        mCryptoCurrency = new CryptoCurrency();
    }

    public void stopRunning() {
        mIsRunning = false;
    }

    protected boolean isRunning() {
        return mIsRunning;
    }

    protected AsyncCallback getCallback() {
        return mCallback;
    }

    protected CryptoCurrency getCryptoCurrency() {
        return mCryptoCurrency;
    }
}
