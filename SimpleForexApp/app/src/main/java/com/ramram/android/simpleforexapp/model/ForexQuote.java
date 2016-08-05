package com.ramram.android.simpleforexapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dah4oz on 05.08.16.
 */
public class ForexQuote {

    @SerializedName("ChangeOrientation")
    private int changeOrientation;

    @SerializedName("Bid")
    private float bId;

    @SerializedName("Ask")
    private float ask;

    @SerializedName("High")
    private float high;

    @SerializedName("Low")
    private float low;

    @SerializedName("Change")
    private float change;

    @SerializedName("Currency")
    private String currency;

}
