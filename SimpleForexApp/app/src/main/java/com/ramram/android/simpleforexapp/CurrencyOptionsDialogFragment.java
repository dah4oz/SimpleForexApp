package com.ramram.android.simpleforexapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dah4oz on 07.08.16.
 */
public class CurrencyOptionsDialogFragment extends DialogFragment {

    private static final String TAG = CurrencyOptionsDialogFragment.class.getSimpleName();
    private View mRootView;
    private LinearLayout mContainer;
    private List<String> allCurencies = new ArrayList<>();
    private static Set<String> selectedCurrencies = new HashSet<>();
    private OnCurrencySelectedListener mCallback;

    public interface OnCurrencySelectedListener{
        void currencySelected(List<String> selected);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            mCallback = (OnCurrencySelectedListener) getActivity();
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_remove_currency_layout, container, false);
        mContainer = (LinearLayout) mRootView.findViewById(R.id.container);
        Button okBtn = (Button) mRootView.findViewById(R.id.dialog_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedCurrencies();
            }
        });
        initAllCurrencies();
        showCurrencyList();
        return mRootView;
    }

    private void setSelectedCurrencies(){
        List<String> selected = new ArrayList<>();
        int size = mContainer.getChildCount();
        for(int i = 0; i < size; i++){
            RelativeLayout item = (RelativeLayout) mContainer.getChildAt(i);
            CheckBox checkBox = (CheckBox) item.findViewById(R.id.currency_checkbox);
            if(checkBox.isChecked()){
                selected.add(allCurencies.get(i));
            }
        }
        mCallback.currencySelected(selected);
        dismiss();
    }

    private void initAllCurrencies(){
        allCurencies.add("EURUSD");
        allCurencies.add("GBPUSD");
        allCurencies.add("USDCHF");
        allCurencies.add("USDJPY");
        allCurencies.add("AUDUSD");
        allCurencies.add("USDCAD");
        allCurencies.add("EURGBP");
        allCurencies.add("EURJPY");
        allCurencies.add("AUDCAD");
    }

    private void showCurrencyList() {
        mContainer.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (String currency : allCurencies){
            RelativeLayout row = (RelativeLayout) inflater.inflate(R.layout.currency_option_item, null);
            TextView currencyName = (TextView) row.findViewById(R.id.currency_name);
            currencyName.setText(currency);
            CheckBox checkBox = (CheckBox) row.findViewById(R.id.currency_checkbox);
            checkBox.setChecked(selectedCurrencies.contains(currency));
            mContainer.addView(row);
        }
    }


    public static CurrencyOptionsDialogFragment newInstance(List<String> selected){
        selectedCurrencies = new HashSet<>(selected);
        return new CurrencyOptionsDialogFragment();
    }
}
