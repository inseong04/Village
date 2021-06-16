package com.example.village.screen.productwriting;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.village.R;
import com.example.village.databinding.SpinnerView1Binding;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class SpinnerAdapter extends BaseAdapter {

    Context mContext;
    List<String> data;
    LayoutInflater layoutInflater;
    public SpinnerAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
        this.layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.spinner_view1, parent, false);
        Log.e("version","version 1");
        if(data!= null) {
            String text = data.get(position);
            ((TextView) convertView.findViewById(R.id.spinnerText)).setText(text);

            }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.spinner_view2, parent, false);
        Log.e("asdf","asdf");
        if(position == 0)
            ((LinearLayout) convertView.findViewById(R.id.view2LinearLayout)).setBackground(ContextCompat.getDrawable(mContext, R.drawable.spinner_dropdown_start));
        else if (position == data.size()-1)
            ((LinearLayout) convertView.findViewById(R.id.view2LinearLayout)).setBackground(ContextCompat.getDrawable(mContext, R.drawable.spinner_dropdown_end));
        else
            ((LinearLayout) convertView.findViewById(R.id.view2LinearLayout)).setBackground(ContextCompat.getDrawable(mContext, R.drawable.spinner_dropdown_middle));

        String text = data.get(position);
        ((TextView) convertView.findViewById(R.id.spinnerTv)).setText(text);

        return convertView;
    }


    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
