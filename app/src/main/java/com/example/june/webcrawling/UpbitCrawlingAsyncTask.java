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

public class UpbitCrawlingAsyncTask extends AsyncTask<String, Void, String>{
    private final String BINANCE_URL_BASE = "https://www.binance.com/api/v1/aggTrades?limit=1&symbol=TRX";

    @Override
    protected String doInBackground(String... params) {
        String url = BINANCE_URL_BASE + params[0];
        Document doc = null;
        Elements elem = null;
        String str = null;
        double price = 0;

        try {
            doc = Jsoup.connect(url).ignoreContentType(true).get();
            JSONArray jsonArray = new JSONArray(doc.text());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            price = (double) jsonObject.get("tradePrice");
            Log.d("JUNE", "" + price);
            //elem = doc.select("tradePrice");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //mCrawlingResult = elem.text();

        //return elem.text();
        DecimalFormat df = new DecimalFormat("#########################.00");
        return df.format(price);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //TextView textView = (TextView) findViewById(R.id.text_view);

        //textView.setText(s);
    }
}
