package com.andraganoid.gameofmath.Fast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        ViewHolder vh = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rv = inflater.inflate(R.layout.fast_card_view, parent, false);

        vh.f1 = rv.findViewById(R.id.fast_1);
        vh.f2 = rv.findViewById(R.id.fast_2);
        vh.f4 = rv.findViewById(R.id.fast_4);
        vh.hs = rv.findViewById(R.id.fast_hs);
        vh.go = rv.findViewById(R.id.fast_go);

        vh.f1.setText(fLine.get(position).getfName());
        vh.f2.setText(fLine.get(position).getfDesc());
        vh.f4.setText(context.getResources().getString(R.string.best_time) + " " + fLine.get(position).getfScore());
        vh.hs.setTag(position);
        vh.go.setTag(position);

        return rv;
    }

    private static class ViewHolder {
        TextView f1;
        TextView f2;
        TextView f4;
        TextView hs;
        TextView go;

    }
}
