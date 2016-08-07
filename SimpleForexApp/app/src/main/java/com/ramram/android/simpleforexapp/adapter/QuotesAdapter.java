package com.ramram.android.simpleforexapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
    private Context context;
    private List<ForexQuote> quoteList;

    public QuotesAdapter(List<ForexQuote> list, boolean useGrid, Context ctx){
        quoteList = list;
        isGrid = useGrid;
        context = ctx;
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

        if(quote.getChangeOrientation() == 1){
            holder.buyTv.setTextColor(ContextCompat.getColor(context, R.color.color_up));
            holder.sellTv.setTextColor(ContextCompat.getColor(context, R.color.color_up));
        }
        else if(quote.getChangeOrientation() == 2){
            holder.buyTv.setTextColor(ContextCompat.getColor(context, R.color.color_down));
            holder.sellTv.setTextColor(ContextCompat.getColor(context, R.color.color_down));
        }

        holder.buyTv.setText(String.format("%.4f", quote.getbId()));
        holder.sellTv.setText(String.format("%.4f", quote.getAsk()));
        holder.currencyTv.setText(quote.getCurrency());
        holder.sellBtn.setOnClickListener(clickListener);
        holder.sellBtn.setOnClickListener(clickListener);
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
