package com.ramram.android.simpleforexapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

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

    private static final String REQUEST_URL = "http://eu.tradenetworks.com/QuotesBox/quotes/GetQuotesBySymbols";
    private static final int REQUEST_DELAY = 500;

    private List<ForexQuote> mQuotesList = new ArrayList<>();
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestData();

    }

    private void requestData(){
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        CustomRequest<QuotesResponse> quoteRequest = new CustomRequest<>(REQUEST_URL, QuotesResponse.class, headers, params,
                responseListener, errorListener);
        NetworkManager.getInstance(this).addToRequestQue(quoteRequest);
    }


    Response.Listener<QuotesResponse> responseListener = new Response.Listener<QuotesResponse>() {
        @Override
        public void onResponse(QuotesResponse quotesResponse) {
            mQuotesList = quotesResponse.getQuoteList();
            mHandler.postDelayed(RequestScheduler, REQUEST_DELAY);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //TODO: show an error message...
        }
    };

    Runnable RequestScheduler = new Runnable() {
        @Override
        public void run() {
            requestData();
        }
    };
}
