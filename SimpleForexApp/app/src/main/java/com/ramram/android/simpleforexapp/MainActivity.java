package com.ramram.android.simpleforexapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity implements CurrencyOptionsDialogFragment.OnCurrencySelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String REQUEST_URL = "http://eu.tradenetworks.com/QuotesBox/quotes/GetQuotesBySymbols";
    private static final int REQUEST_DELAY = 500;

    private boolean isGrid;
    private String sessionId;
    private List<ForexQuote> mQuotesList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private List<String> selectedCurrencyList = new ArrayList<>();

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyOptionsDialogFragment fragment = CurrencyOptionsDialogFragment.newInstance(selectedCurrencyList);
                fragment.show(getSupportFragmentManager(), "CurrencyOptionsDialogFragment");
            }
        });

        setSupportActionBar(mToolbar);

        initQuotesList();
        initDefaultCurrency();
        requestData();

    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(RequestScheduler);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        RequestScheduler = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_row){
            isGrid = false;
            initQuotesList();
        }
        else if(item.getItemId() == R.id.menu_grid){
            isGrid = true;
            initQuotesList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initQuotesList(){
        mAdapter = new QuotesAdapter(mQuotesList, isGrid, getApplicationContext(), currencyActionSelectedListener);
        RecyclerView.LayoutManager mLayoutManager = isGrid ? new GridLayoutManager(this, 2) :
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void initDefaultCurrency(){
        selectedCurrencyList.add("EURUSD");
        selectedCurrencyList.add("GBPUSD");
        selectedCurrencyList.add("USDCHF");
        selectedCurrencyList.add("USDJPY");
    }

    QuotesAdapter.OnCurrencyActionSelectedListener currencyActionSelectedListener = new QuotesAdapter.OnCurrencyActionSelectedListener() {
        @Override
        public void onActionSelected(String currencyName, float value) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Currency - " + currencyName + " rate - " + value, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    };

    private void requestData(){

        if(!NetworkManager.isOnline(getApplicationContext())){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.no_net_alert, Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("ASP.NET_SessionId", sessionId);

        Map<String, String> params = new HashMap<>();
        params.put("languageCode", "en-US");
        params.put("symbols", TextUtils.join(",", selectedCurrencyList));

        QuotesRequest quoteRequest = new QuotesRequest(Request.Method.POST, REQUEST_URL, headers, params,
                responseListener, errorListener);
        NetworkManager.getInstance(this).addToRequestQue(quoteRequest);
    }


    Response.Listener<QuotesResponse> responseListener = new Response.Listener<QuotesResponse>() {
        @Override
        public void onResponse(QuotesResponse quotesResponse) {
            mQuotesList = quotesResponse.getQuoteList();
            sessionId = quotesResponse.getSessionId();
            initQuotesList();
            mHandler.postDelayed(RequestScheduler, REQUEST_DELAY);
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

    @Override
    public void currencySelected(List<String> selected) {
        selectedCurrencyList.clear();
        selectedCurrencyList.addAll(selected);
        requestData();
    }
}
