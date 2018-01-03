package com.example.june.webcrawling;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.june.webcrawling.crawlingasynctask.AsyncCallback;
import com.example.june.webcrawling.crawlingasynctask.CrawlingAsyncTaskBinance;
import com.example.june.webcrawling.crawlingasynctask.CrawlingAsyncTaskUpbit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private static final int MSG_UPDATE_BINANCE = 0;
    private static final int MSG_UPDATE_UPBIT = 1;

    private CrawlingAsyncTaskBinance mBnCrawlingAsyncTask;
    private CrawlingAsyncTaskUpbit mUbCrawlingAsyncTask;
    private CryptoCurrency mBinanceCurrency = null;
    private CryptoCurrency mUpbitCurrency = null;
    private UpdateHandler mUpdateHandler = new UpdateHandler();

    private RecyclerView mContentRecyclerView;
    private ContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentRecyclerView = (RecyclerView) findViewById(R.id.content_recycler_view);
        mContentRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mContentRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ContentAdapter(this);
        mContentRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBnCrawlingAsyncTask = new CrawlingAsyncTaskBinance(mBinanceAsyncCallback);
        mBnCrawlingAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        mUbCrawlingAsyncTask = new CrawlingAsyncTaskUpbit(mUpbitAsyncCallback);
        mUbCrawlingAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBnCrawlingAsyncTask.stopRunning();
        mUbCrawlingAsyncTask.stopRunning();
    }

    private AsyncCallback mBinanceAsyncCallback = new AsyncCallback() {
        @Override
        public void onFinish(CryptoCurrency currency) {
            mUpdateHandler.obtainMessage(MSG_UPDATE_BINANCE, currency).sendToTarget();
        }
    };

    private AsyncCallback mUpbitAsyncCallback = new AsyncCallback() {
        @Override
        public void onFinish(CryptoCurrency currency) {
            mUpdateHandler.obtainMessage(MSG_UPDATE_UPBIT, currency).sendToTarget();
        }
    };

    public class UpdateHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, "handleMessage");
            switch (msg.what) {
                case MSG_UPDATE_BINANCE:
                    mBinanceCurrency = (CryptoCurrency) msg.obj;
//                    if (mUpbitCurrency != null) {
//                        Log.d(TAG, "binance");
//                        String btc = mBinanceCurrency.getCurrencyList().get(0);
//                        //mTextView.setText(df.format(mBinanceCurrency.getCurrencyPrice(btc)));
//                    }
                    mAdapter.setBinancePrices(mBinanceCurrency.getCurrencyPrices());
                    break;
                case MSG_UPDATE_UPBIT:
                    mUpbitCurrency = (CryptoCurrency) msg.obj;
//                    if (mBinanceCurrency != null) {
//                        Log.d(TAG, "upbit");
//                        String btc = mUpbitCurrency.getCurrencyList().get(0);
//                        // mTextView.setText(df.format(mUpbitCurrency.getCurrencyPrice(btc)));
//                    }
                    mAdapter.setUpbitPrices(mUpbitCurrency.getCurrencyPrices());
                    break;
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
