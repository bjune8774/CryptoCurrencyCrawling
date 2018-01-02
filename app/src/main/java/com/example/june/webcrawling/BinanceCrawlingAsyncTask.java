package com.example.june.webcrawling;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;

/**
 * Created by JeongByungJune on 2018-01-01.
 */

public class BinanceCrawlingAsyncTask extends AsyncTask<Void, Void, Void>{
    private final String BINANCE_URL_BASE = "https://www.binance.com/api/v1/aggTrades?limit=1&symbol=";
    private final String BTC_MARKET = "BTC";
    private final String USDT_MARKET = "USDT";
    private CryptoCurrency mBinanceCurrency = new CryptoCurrency();
    private AsyncCallback mCallback;
    private boolean mIsRunning;

    public BinanceCrawlingAsyncTask(AsyncCallback cb) {
        mCallback = cb;
        mIsRunning = true;
    }

    public void stopRunning() {
        mIsRunning = false;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String url;
        Document doc = null;
        double price = 0;

        while(mIsRunning) {
            for(String currencyName : mBinanceCurrency.getCurrencyList()) {
                if(currencyName.equals("BTC")) {
                    url = BINANCE_URL_BASE + currencyName + USDT_MARKET;
                } else {
                    url = BINANCE_URL_BASE + currencyName + BTC_MARKET;
                }
                Log.d("JUNE", url);
                try {
                    doc = Jsoup.connect(url).ignoreContentType(true).get();
                    JSONArray jsonArray = new JSONArray(doc.text());
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    price = (double) Double.parseDouble((String) jsonObject.get("p"));
                    Log.d("JUNE", "" + price);
                    //elem = doc.select("tradePrice");
                } catch (Exception e) {
                    e.printStackTrace();
                    price = 0;
                }

                mBinanceCurrency.setCurrencyPrice(currencyName, price);
            }
            mCallback.onFinish(mBinanceCurrency);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //mCrawlingResult = elem.text();

        //return elem.text();
        DecimalFormat df = new DecimalFormat("#########################.00");
        //return df.format(price);

        return null;
    }
}
