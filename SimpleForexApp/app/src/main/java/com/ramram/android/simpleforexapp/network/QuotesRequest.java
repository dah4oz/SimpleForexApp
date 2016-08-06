package com.ramram.android.simpleforexapp.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ramram.android.simpleforexapp.model.QuotesResponse;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by dah4oz on 06.08.16.
 */
public class QuotesRequest extends Request<QuotesResponse> {

    private static final String TAG = QuotesRequest.class.getSimpleName();
    private Map<String, String> headers;
    private Map<String, String> params;
    private Response.Listener<QuotesResponse> responseListener;


    public QuotesRequest(int method, String url, Map<String, String> headers,
                         Map<String, String> params, Response.Listener<QuotesResponse> responseListener,
                         Response.ErrorListener listener) {
        super(method, url, listener);
        this.headers = headers;
        this.params = params;
        this.responseListener = responseListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        for(Map.Entry<String, String> param : params.entrySet()){
            Log.d(TAG, "@param - " + param.getKey() + " value - " + param.getValue());
        }
        return params != null ? params : super.getParams();
    }

    @Override
    protected Response<QuotesResponse> parseNetworkResponse(NetworkResponse networkResponse) {

        String sessionHeader = networkResponse.headers.get("Set-Cookie");
        int start = sessionHeader.indexOf("=") + 1;
        int end = sessionHeader.indexOf(";");
        String sessionId = sessionHeader.substring(start, end);
        QuotesResponse response = new QuotesResponse();
        response.setSessionId(sessionId);

        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            String json1 = json.replace("(", "");
            String json2 = json1.replace(")", "");
            response.fromJson(json2);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(QuotesResponse response) {
        responseListener.onResponse(response);
    }
}
