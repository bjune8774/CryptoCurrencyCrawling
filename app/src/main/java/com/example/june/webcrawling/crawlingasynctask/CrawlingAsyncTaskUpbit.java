package com.example.june.webcrawling.crawlingasynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.june.webcrawling.AsyncCallback;
import com.example.june.webcrawling.CryptoCurrency;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;

/**
 * Created by JeongByungJune on 2018-01-01.
 */

public class CrawlingAsyncTaskUpbit extends CrawlingAsyncTaskBase {
    private final String TAG = this.getClass().getName();
    private final String UPBIT_URL_BASE = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.";
    private CryptoCurrency mUpbitCurrency = new CryptoCurrency();

    public CrawlingAsyncTaskUpbit(AsyncCallback cb) {
        super(cb);
    }

    @Override
    protected Void doInBackground(Void... params) {
        String url;
        Document doc = null;
        double price = 0;

        while (isRunning()) {
            for (String currencyName : mUpbitCurrency.getCurrencyList()) {
                url = UPBIT_URL_BASE + KRW_MARKET + "-" + currencyName;
                //Log.d(TAG, url);

                try {
                    doc = Jsoup.connect(url).ignoreContentType(true).get();
                    JSONArray jsonArray = new JSONArray(doc.text());
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    price = (double) jsonObject.get("tradePrice");
                    //Log.d(TAG, "" + price);
                } catch (Exception e) {
                    e.printStackTrace();
                    price = 0;
                }

                mUpbitCurrency.setCurrencyPrice(currencyName, price);
            }
            getCallback().onFinish(mUpbitCurrency);

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
