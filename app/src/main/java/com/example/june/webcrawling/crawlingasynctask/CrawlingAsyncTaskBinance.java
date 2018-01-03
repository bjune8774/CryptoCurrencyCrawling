package com.example.june.webcrawling.crawlingasynctask;

import com.example.june.webcrawling.CryptoCurrency;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
            for (int i = 0; i < CryptoCurrency.getCurrencyList().size(); i++) {
                String currencyName = CryptoCurrency.getCurrencyName(i);

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
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
