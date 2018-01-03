package com.example.june.webcrawling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by JeongByungJune on 2018-01-03.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
    private Context mContext;
    private HashMap<String, Double> mUpbitPrices = null;
    private HashMap<String, Double> mBinancePrices = null;

    private DecimalFormat mDf = new DecimalFormat("#,###.##");

    public ContentAdapter(Context context) {
        mContext = context;
    }

    public void setUpbitPrices(HashMap<String, Double> upbitPrices) {
        mUpbitPrices = upbitPrices;
    }

    public void setBinancePrices(HashMap<String, Double> binancePrices) {
        mBinancePrices = binancePrices;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view, parent, false);
        ContentHolder holder = new ContentHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        String currency = CryptoCurrency.getCurrencyList().get(position);

        holder.mCurrencyNameText.setText(currency);

        if (mUpbitPrices != null && mBinancePrices != null) {
            double ubKrwPrice = mUpbitPrices.get(currency);
            double ubUsdPrice = ubKrwPrice / 1080;

            holder.mUpbitKrwText.setText(mDf.format(mUpbitPrices.get(currency)) + "원");
            holder.mUpbitUsdText.setText(mDf.format(mUpbitPrices.get(currency) / 1080) + "$");

            double bnUsdBtcPrice = mBinancePrices.get("BTC");
            double bnUsdPrice = 0;

            if(currency.equals("BTC")) {
                bnUsdPrice = bnUsdBtcPrice;
            } else {
                bnUsdPrice = mBinancePrices.get(currency) * bnUsdBtcPrice;
            }

            double bnKrwPrice = bnUsdPrice * 1080;

            holder.mBinanceKrwText.setText(mDf.format(bnKrwPrice) + "원");
            holder.mBinanceUsdText.setText(mDf.format(bnUsdPrice) + "$");

            holder.mKrwPremium.setText(mDf.format(((ubUsdPrice / bnUsdPrice) - 1) * 100) + "%");
        }
    }

    @Override
    public int getItemCount() {
        return CryptoCurrency.getCurrencyList().size();
    }

    public class ContentHolder extends RecyclerView.ViewHolder {
        private TextView mCurrencyNameText;
        private TextView mUpbitKrwText;
        private TextView mUpbitUsdText;
        private TextView mBinanceKrwText;
        private TextView mBinanceUsdText;
        private TextView mKrwPremium;

        public ContentHolder(View itemView) {
            super(itemView);
            mCurrencyNameText = (TextView) itemView.findViewById(R.id.content_currency_name);
            mUpbitKrwText = (TextView) itemView.findViewById(R.id.content_upbit_krw);
            mUpbitUsdText = (TextView) itemView.findViewById(R.id.content_upbit_usd);
            mBinanceKrwText = (TextView) itemView.findViewById(R.id.content_binance_krw);
            mBinanceUsdText = (TextView) itemView.findViewById(R.id.content_binance_usd);
            mKrwPremium = (TextView) itemView.findViewById(R.id.content_krw_premium);
        }
    }
}
