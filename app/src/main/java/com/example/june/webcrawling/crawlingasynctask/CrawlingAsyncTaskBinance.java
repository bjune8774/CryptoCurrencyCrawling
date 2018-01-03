package com.example.june.webcrawling.crawlingasynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.june.webcrawling.AsyncCallback;
import com.example.june.webcrawling.CryptoCurrency;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.DecimalFormat;

/**
 * Created by JeongByungJune on 2018-01-01.
 */

public class CrawlingAsyncTaskBinance extends CrawlingAsyncTaskBase {
    private final String TAG = this.getClass().getName();
    private final String BINANCE_URL_BASE = "https://www.binance.com/api/v1/aggTrades?limit=1&symbol=";
    private CryptoCurrency mBinanceCurrency = new CryptoCurrency();

    public CrawlingAsyncTaskBinance(AsyncCallback cb) {
        super(cb);
    }

    @Override
    protected Void doInBackground(Void... params) {
        String url;
        Document doc = null;
        double price = 0;

        while(isRunning()) {
            for(String currencyName : mBinanceCurrency.getCurrencyList()) {
                if(currencyName.equals("BTC")) {
                    url = BINANCE_URL_BASE + currencyName + USDT_MARKET;
                } else {
                    url = BINANCE_URL_BASE + currencyName + BTC_MARKET;
                }
                //Log.d(TAG, url);

                try {
                    doc = Jsoup.connect(url).ignoreContentType(true).get();
                    JSONArray jsonArray = new JSONArray(doc.text());
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    price = (double) Double.parseDouble((String) jsonObject.get("p"));
                    //Log.d(TAG, "" + price);
                } catch (Exception e) {
                    e.printStackTrace();
                    price = 0;
                }

                mBinanceCurrency.setCurrencyPrice(currencyName, price);
            }
            getCallback().onFinish(mBinanceCurrency);

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
