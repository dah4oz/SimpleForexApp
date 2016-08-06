package com.ramram.android.simpleforexapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ramram.android.simpleforexapp.R;
import com.ramram.android.simpleforexapp.model.ForexQuote;

import java.util.List;

/**
 * Created by dah4oz on 06.08.16.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.MyViewHolder>{

    private static final String TAG = QuotesAdapter.class.getSimpleName();
    private boolean isGrid;
    private List<ForexQuote> quoteList;

    public QuotesAdapter(List<ForexQuote> list, boolean useGrid){
        quoteList = list;
        isGrid = useGrid;
    }

    @Override
    public QuotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(isGrid ?
                R.layout.quotes_grid_item : R.layout.quotes_list_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuotesAdapter.MyViewHolder holder, int position) {
        ForexQuote quote = quoteList.get(position);
        holder.buyTv.setText(String.format("%.4f", quote.getbId()));
        holder.sellTv.setText(String.format("%.4f", quote.getAsk()));
        holder.currencyTv.setText(quote.getCurrency());
        holder.sellBtn.setOnClickListener(clickListener);
        holder.sellBtn.setOnClickListener(clickListener);

        Log.d(TAG, "@Bound - " + quote.getCurrency());
    }

    @Override
    public int getItemCount() {
        return quoteList != null ? quoteList.size() : 0;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView buyTv, sellTv, currencyTv;
        Button buyBtn, sellBtn;

        public MyViewHolder(View view) {
            super(view);
            buyBtn = (Button) view.findViewById(R.id.buy_btn);
            buyTv = (TextView) view.findViewById(R.id.buy_tv);
            sellBtn = (Button) view.findViewById(R.id.sell_btn);
            sellTv = (TextView) view.findViewById(R.id.sell_tv);
            currencyTv = (TextView) view.findViewById(R.id.currency_tv);
        }
    }
}
