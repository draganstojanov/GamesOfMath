package com.andraganoid.gameofmath.Fast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.andraganoid.gameofmath.Fast.FastData;
import com.andraganoid.gameofmath.R;

import java.util.List;


public class FastAdapter extends ArrayAdapter {

    private Context context;
    private List<FastData> fLine;


    public FastAdapter(@NonNull Context context, List<FastData> fl) {
        super(context, 0, fl);
        this.context = context;
        fLine = fl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rv = inflater.inflate(R.layout.fast_card_view, parent, false);
        ((TextView) rv.findViewById(R.id.fast_1)).setText(fLine.get(position).getfName());
        ((TextView) rv.findViewById(R.id.fast_2)).setText(fLine.get(position).getfDesc());
        ((TextView) rv.findViewById(R.id.fast_4)).setText(context.getResources().getString(R.string.best_time) + " " + fLine.get(position).getfScore());
        ((TextView) rv.findViewById(R.id.fast_hs)).setTag(position);
        ((TextView) rv.findViewById(R.id.fast_go)).setTag(position);

        return rv;
    }
}
