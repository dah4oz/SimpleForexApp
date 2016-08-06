package com.ramram.android.simpleforexapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ramram.android.simpleforexapp.adapter.QuotesAdapter;
import com.ramram.android.simpleforexapp.model.ForexQuote;
import com.ramram.android.simpleforexapp.model.QuotesResponse;
import com.ramram.android.simpleforexapp.network.NetworkManager;
import com.ramram.android.simpleforexapp.network.QuotesRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String REQUEST_URL = "http://eu.tradenetworks.com/QuotesBox/quotes/GetQuotesBySymbols";
    private static final int REQUEST_DELAY = 500;

    private String sessionId;
    private List<ForexQuote> mQuotesList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private List<String> currencyList = new ArrayList<>();

    private Toolbar mToolbar;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private QuotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fab.setOnClickListener(clickListener);

        setSupportActionBar(mToolbar);

        initQuotesList();
        initDefaultCurrency();
        requestData();


    }

    private void initQuotesList(){
        mAdapter = new QuotesAdapter(mQuotesList, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
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

        QuotesRequest quoteRequest = new QuotesRequest(Request.Method.POST, REQUEST_URL, headers, params,
                responseListener, errorListener);
        NetworkManager.getInstance(this).addToRequestQue(quoteRequest);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    Response.Listener<QuotesResponse> responseListener = new Response.Listener<QuotesResponse>() {
        @Override
        public void onResponse(QuotesResponse quotesResponse) {
            mQuotesList = quotesResponse.getQuoteList();
            //mHandler.postDelayed(RequestScheduler, REQUEST_DELAY);
            mAdapter.notifyDataSetChanged();
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
