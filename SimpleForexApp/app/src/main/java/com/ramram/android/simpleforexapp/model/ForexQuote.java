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

    public int getChangeOrientation() {
        return changeOrientation;
    }

    public void setChangeOrientation(int changeOrientation) {
        this.changeOrientation = changeOrientation;
    }

    public float getbId() {
        return bId;
    }

    public void setbId(float bId) {
        this.bId = bId;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
