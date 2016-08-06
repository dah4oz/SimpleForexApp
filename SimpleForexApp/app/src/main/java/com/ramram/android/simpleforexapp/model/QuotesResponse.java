package com.ramram.android.simpleforexapp.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dah4oz on 06.08.16.
 */
public class QuotesResponse {

    List<ForexQuote> quoteList = new ArrayList<>();
    String sessionId;

    public List<ForexQuote> getQuoteList(){
        return quoteList;
    }
    public String getSessionId(){
        return sessionId;
    }

    public void setSessionId(String id){
        sessionId = id;
    }

    public void fromJson(String json){
        JsonElement jsonElement = new JsonParser().parse(json);
        if(jsonElement.isJsonArray()){
            JsonArray array = jsonElement.getAsJsonArray();
            Iterator iterator = array.iterator();

            while(iterator.hasNext()){
                JsonElement jsonEntry = (JsonElement) iterator.next();
                quoteList.add(parseJson(jsonEntry));
            }
        }
        else if(jsonElement.isJsonObject()){
            quoteList.add(parseJson(jsonElement.getAsJsonObject()));
        }
    }

    private static ForexQuote parseJson(JsonElement jsonElement){
        Gson gson = new Gson();
        ForexQuote forexQuote = gson.fromJson(jsonElement, ForexQuote.class);

        return forexQuote;
    }

}
