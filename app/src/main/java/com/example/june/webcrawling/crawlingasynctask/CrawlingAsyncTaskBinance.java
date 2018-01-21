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
    private final String CODE_BTC = getCryptoCurrency().getCode(CryptoCurrency.INDEX_BTC);

    public CrawlingAsyncTaskBinance(AsyncCallback cb) {
        super(cb);
    }

    @Override
    protected Void doInBackground(Void... params) {
        CryptoCurrency cryptoCurrency = getCryptoCurrency();
        String url = null;
        double priceBtcUsdtMarket = 0;
        double priceBtcMarket = 0;

        while (isRunning()) {
            url = BINANCE_URL_BASE + CODE_BTC + USDT_MARKET;
            priceBtcUsdtMarket = getPriceFromUrl(url);

            for (CryptoCurrency.Coin coin : cryptoCurrency.getCoinList()) {
                String code = coin.getCode();

                if (code.equals(CODE_BTC)) {
                    priceBtcMarket = 1;
                } else {
                    url = BINANCE_URL_BASE + code + BTC_MARKET;
                    priceBtcMarket = getPriceFromUrl(url);
                }

                double priceUsd = priceBtcMarket * priceBtcUsdtMarket;
                double priceKrw = priceUsd * 1080;

                coin.setExchangePrice(CryptoCurrency.EXCHANGE_BINANCE, priceKrw, priceUsd);
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
            price = (double) Double.parseDouble((String) jsonObject.get("p"));
            //Log.d(TAG, "" + price);
        } catch (Exception e) {
            e.printStackTrace();
            price = 0;
        }

        return price;
    }
}
