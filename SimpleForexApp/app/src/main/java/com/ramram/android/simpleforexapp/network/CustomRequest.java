package com.ramram.android.simpleforexapp.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by dah4oz on 05.08.16.
 */
public class CustomRequest<T> extends Request<T>{

    private static final String TAG = CustomRequest.class.getSimpleName();
    private Gson gson = new Gson();
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final Class<T> clazz;
    private final Response.Listener<T> listener;

    public CustomRequest(String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
                         Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.headers = headers;
        this.params = params;
        this.clazz = clazz;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {

        Map<String, String> headers = networkResponse.headers;
        String sessionHeader = networkResponse.headers.get("Set-Cookie");
        int start = sessionHeader.indexOf("=") + 1;
        int end = sessionHeader.indexOf(";");
        String sessionId = sessionHeader.substring(start, end);
        Log.d(TAG, "@SessionId = " + sessionId);

        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            String json1 = json.replace("(", "");
            String json2 = json1.replace(")", "");



            return Response.success(gson.fromJson(json2, clazz), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
