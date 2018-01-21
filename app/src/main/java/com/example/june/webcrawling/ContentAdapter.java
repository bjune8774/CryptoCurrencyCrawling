package com.example.june.webcrawling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by JeongByungJune on 2018-01-03.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
    private final String TAG = this.getClass().getName();
    private Context mContext;
    private List<CryptoCurrency.Coin> mCoinList = null;
    private DecimalFormat mPriceDf = new DecimalFormat("#,###");
    private DecimalFormat mPercentDf = new DecimalFormat("##.#");

    private String mLeftExchange;
    private String mRightExchange;

    public ContentAdapter(Context context) {
        mContext = context;
    }

    public void setCoinList(List<CryptoCurrency.Coin> coinList) {
        if(mCoinList == null) {
            mCoinList = coinList;
        }
    }

    public void setLeftExchange(String exchange) {
        mLeftExchange = exchange;
    }

    public void setRightExchange(String exchange) {
        mRightExchange = exchange;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view, parent, false);
        ContentHolder holder = new ContentHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        CryptoCurrency.Coin coin = mCoinList.get(position);

        holder.mTextCoinName.setText(coin.getNameKr());
        holder.mTextCoinCode.setText(coin.getCode() + "/KRW");

        if (mCoinList != null) {
            CryptoCurrency.Price leftPrice = coin.getExchangePrice(mLeftExchange);
            CryptoCurrency.Price rightPrice = coin.getExchangePrice(mRightExchange);

            if (leftPrice != null && rightPrice != null) {
                holder.mTextLeftPrice.setText(mPriceDf.format(leftPrice.krw) + "원");
//                holder.mTextLeftPrice.setText(mPriceDf.format(leftPrice.usd) + "$");

                holder.mTextRightPrice.setText(mPriceDf.format(rightPrice.krw) + "원");
//                holder.mTextRightPrice.setText(mPriceDf.format(rightPrice.usd) + "$");

                holder.mTextPremium.setText(mPercentDf.format(((leftPrice.krw / rightPrice.krw) - 1) * 100) + "%");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mCoinList != null) {
            return mCoinList.size();
        }
        return 0;
    }

    public class ContentHolder extends RecyclerView.ViewHolder {
        private ImageView mImageCoin;
        private TextView mTextCoinName;
        private TextView mTextCoinCode;
        private TextView mTextLeftPrice;
        private TextView mTextRightPrice;
        private TextView mTextPremium;

        public ContentHolder(View itemView) {
            super(itemView);
            mImageCoin = (ImageView) itemView.findViewById(R.id.content_coin_icon);
            mTextCoinName = (TextView) itemView.findViewById(R.id.content_coin_name);
            mTextCoinCode = (TextView) itemView.findViewById(R.id.content_coin_code);
            mTextLeftPrice = (TextView) itemView.findViewById(R.id.content_left_price);
            mTextRightPrice = (TextView) itemView.findViewById(R.id.content_right_price);
            mTextPremium = (TextView) itemView.findViewById(R.id.content_premium);
        }
    }
}
