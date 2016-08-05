package com.ramram.android.simpleforexapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ramram.android.simpleforexapp.model.ForexQuote;
import com.ramram.android.simpleforexapp.network.CustomRequest;
import com.ramram.android.simpleforexapp.network.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> headers = new HashMap<>();
        CustomRequest<ForexQuote> quoteRequest = new CustomRequest<>("", ForexQuote.class, headers, responseListener, errorListener);
        NetworkManager.getInstance(this).addToRequestQue(quoteRequest);
    }

    Response.Listener<ForexQuote> responseListener = new Response.Listener<ForexQuote>() {
        @Override
        public void onResponse(ForexQuote forexQuote) {

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };
}
