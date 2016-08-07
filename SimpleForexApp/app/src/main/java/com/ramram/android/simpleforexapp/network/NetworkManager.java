package com.ramram.android.simpleforexapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dah4oz on 05.08.16.
 */
public class NetworkManager {

    private static NetworkManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private NetworkManager(Context ctx){
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new NetworkManager(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        if(nInfo != null && nInfo.isConnectedOrConnecting()) {
            return true;
        }
        else {
            return false;
        }
    }


}
