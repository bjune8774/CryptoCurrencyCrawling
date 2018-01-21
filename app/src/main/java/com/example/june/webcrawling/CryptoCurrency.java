package com.example.june.webcrawling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JeongByungJune on 2018-01-14.
 */

public class CryptoCurrency {
    private static List<Coin> mCoinList = null;

    private static final int MAX_CRYPTO_CURRENCY_NUM = 9;

    public static final String EXCHANGE_BINANCE = "binance";
    public static final String EXCHANGE_UPBIT = "upbit";

    public static final int INDEX_BTC = 0;
    public static final int INDEX_ETH = 1;
    public static final int INDEX_ETC = 2;
    public static final int INDEX_MTL = 3;
    public static final int INDEX_BCC = 4;
    public static final int INDEX_SNT = 5;
    public static final int INDEX_STORJ = 6;
    public static final int INDEX_OMG = 7;
    public static final int INDEX_POWR = 8;

    private static String[] mCode = {"BTC", "ETH", "ETC", "MTL", "BCC", "SNT", "STORJ", "OMG", "POWR"};
    private static String[] mNameKr = {"비트코인", "이더리움", "이더리움 클래식", "메탈", "비트코인 캐시",
            "스테이터스 네트워크 토큰", "스토리지", "오미세고", "파워렛져"};
    private static String[] mNameEng = {"Bitcoin", "Ethereum", "Ethereum Classic", "Metal", "Bitcoin Cash",
            "Status Network Token", "Storj", "Omise Go", "Power Ledger"};

    public CryptoCurrency() {
        if(mCoinList == null) {
            mCoinList = new ArrayList<Coin>();
            for (int idx = 0; idx < MAX_CRYPTO_CURRENCY_NUM; idx++) {
                Coin coin = new Coin(mCode[idx], mNameKr[idx], mNameEng[idx]);
                mCoinList.add(coin);
            }
        }
    }

    public String getCode(int currency) {
        return mCode[currency];
    }

    public String[] getCodeList() {
        return mCode;
    }

    public int getMaxCurrencyNum() {
        return MAX_CRYPTO_CURRENCY_NUM;
    }

    public List<Coin> getCoinList() {
        return mCoinList;
    }

    public class Coin {
        private String mCode;
        private String mNameKr;
        private String mNameEng;
        private HashMap<String, Price> mPriceMap = null;

        public Coin(String code, String nameKr, String nameEng) {
            mCode = code;
            mNameKr = nameKr;
            mNameEng = nameEng;
            mPriceMap = new HashMap<String, Price>();
        }

        public String getCode() {
            return mCode;
        }

        public String getNameKr() {
            return mNameKr;
        }

        public String getNameEng() {
            return mNameEng;
        }

        public Price getExchangePrice(String exchange) {
            return mPriceMap.get(exchange);
        }

        public void setExchangePrice(String exchange, double priceKrw, double priceUsd) {
            Price price = new Price(priceKrw, priceUsd);
            mPriceMap.put(exchange, price);
        }
    }

    public static class Price {
        public double krw;
        public double usd;

        public Price(double krw, double usd) {
            this.krw = krw;
            this.usd = usd;
        }
    }
}
