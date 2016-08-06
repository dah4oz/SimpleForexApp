package com.ramram.android.simpleforexapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ramram.android.simpleforexapp.model.ForexQuote;
import com.ramram.android.simpleforexapp.model.QuotesResponse;
import com.ramram.android.simpleforexapp.network.CustomRequest;
import com.ramram.android.simpleforexapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String REQUEST_URL = "http://eu.tradenetworks.com/QuotesBox/quotes/GetQuotesBySymbols";
    private static final int REQUEST_DELAY = 500;

    private List<ForexQuote> mQuotesList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private List<String> currencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDefaultCurrency();
        requestData();
    }

    private void initDefaultCurrency(){
        currencyList.add("EURUSD");
        currencyList.add("GBPUSD");
        currencyList.add("USDCHF");
        currencyList.add("USDJPY");
    }

    private void requestData(){
        Map<String, String> headers = new HashMap<>();
        headers.put("ASP.NET_SessionId", "");
        Map<String, String> params = new HashMap<>();
        params.put("languageCode", "en-US");
        params.put("symbols", TextUtils.join(",", currencyList));
        CustomRequest<QuotesResponse> quoteRequest = new CustomRequest<>(REQUEST_URL, QuotesResponse.class, headers, params,
                responseListener, errorListener);
        NetworkManager.getInstance(this).addToRequestQue(quoteRequest);
    }


    Response.Listener<QuotesResponse> responseListener = new Response.Listener<QuotesResponse>() {
        @Override
        public void onResponse(QuotesResponse quotesResponse) {
            mQuotesList = quotesResponse.getQuoteList();
            //mHandler.postDelayed(RequestScheduler, REQUEST_DELAY);
            Log.d(TAG, "@Quotes list size " + mQuotesList.size());
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            NetworkResponse networkResponse = volleyError.networkResponse;
            if(networkResponse != null && networkResponse.data != null){
                if(networkResponse.statusCode == 400){
                    String json = new String(networkResponse.data);
                    Log.d(TAG, "@VolleyError - " + json);
                }
            }
        }
    };

    Runnable RequestScheduler = new Runnable() {
        @Override
        public void run() {
            requestData();
        }
    };
}
