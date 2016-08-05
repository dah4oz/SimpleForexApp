package com.ramram.android.simpleforexapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dah4oz on 06.08.16.
 */
public class QuotesResponse {

    List<ForexQuote> quoteList = new ArrayList<>();

    public List<ForexQuote> getQuoteList(){
        return quoteList;
    }
}
