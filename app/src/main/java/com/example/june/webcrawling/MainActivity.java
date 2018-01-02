package com.example.june.webcrawling;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private final String UPBIT_URL_BASE = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-BTC";
    private BinanceCrawlingAsyncTask mBnCrawlingAsyncTask;
    private AsyncCallback mAsyncCallback = new AsyncCallback() {
        @Override
        public void onFinish(CryptoCurrency currency) {
            for(String price : currency.getCurrencyList()) {
                Log.d("JUNE", price + " : " + currency.getCurrencyPrice(price));
                mBnCrawlingAsyncTask.stopRunning();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBnCrawlingAsyncTask = new BinanceCrawlingAsyncTask(mAsyncCallback);
        mBnCrawlingAsyncTask.execute();
    }
}
