package com.example.june.webcrawling.crawlingasynctask;

import android.os.AsyncTask;

/**
 * Created by bjune.jeong on 2018-01-03.
 */

public abstract class CrawlingAsyncTaskBase extends AsyncTask<Void, Void, Void> {
    private final String TAG = this.getClass().getName();
    protected final String BTC_MARKET = "BTC";
    protected final String USDT_MARKET = "USDT";
    protected final String KRW_MARKET = "KRW";

    private AsyncCallback mCallback;
    private boolean mIsRunning;

    protected CrawlingAsyncTaskBase(AsyncCallback cb) {
        mCallback = cb;
        mIsRunning = true;
    }

    public void stopRunning() {
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    protected AsyncCallback getCallback() {
        return mCallback;
    }
}
