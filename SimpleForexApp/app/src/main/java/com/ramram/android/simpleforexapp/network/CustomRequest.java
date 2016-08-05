package com.ramram.android.simpleforexapp.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created by dah4oz on 05.08.16.
 */
public class CustomRequest<T> extends Request<T>{

    public CustomRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    protected void deliverResponse(T response) {

    }
}
