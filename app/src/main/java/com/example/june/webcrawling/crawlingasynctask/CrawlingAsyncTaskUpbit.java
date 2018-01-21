package com.example.june.webcrawling.crawlingasynctask;

import com.example.june.webcrawling.CryptoCurrency;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by JeongByungJune on 2018-01-01.
 */

public class CrawlingAsyncTaskUpbit extends CrawlingAsyncTaskBase {
    private final String TAG = this.getClass().getName();

    private final String UPBIT_URL_BASE = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.";

    public CrawlingAsyncTaskUpbit(AsyncCallback cb) {
        super(cb);
    }

    @Override
    protected Void doInBackground(Void... params) {
        CryptoCurrency cryptoCurrency = getCryptoCurrency();
        String url;
        double priceKrwMarket = 0;

        while (isRunning()) {
            for (CryptoCurrency.Coin coin : cryptoCurrency.getCoinList()) {
                String code = coin.getCode();
                url = UPBIT_URL_BASE + KRW_MARKET + "-" + code;
                //Log.d(TAG, url);

                priceKrwMarket = getPriceFromUrl(url);
                double priceUsd = priceKrwMarket / 1080;
                double priceKrw = priceKrwMarket;

                coin.setExchangePrice(CryptoCurrency.EXCHANGE_UPBIT, priceKrw, priceUsd);
            }
            getCallback().onFinish(cryptoCurrency);

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private double getPriceFromUrl(String url) {
        double price = 0;

        try {
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            JSONArray jsonArray = new JSONArray(doc.text());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            price = (double) jsonObject.get("tradePrice");
            //Log.d(TAG, "" + price);
        } catch (Exception e) {
            e.printStackTrace();
            price = 0;
        }

        return price;
    }
}
