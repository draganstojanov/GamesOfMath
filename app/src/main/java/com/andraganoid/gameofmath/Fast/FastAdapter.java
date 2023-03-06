package com.andraganoid.gameofmath.Fast;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andraganoid.gameofmath.R;

import java.util.List;


public class FastAdapter extends ArrayAdapter {

    private Context context;
    private List <FastData> fLine;

    public FastAdapter(@NonNull Context context, List <FastData> fl) {
        super(context, 0, fl);
        this.context = context;
        fLine = fl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.settings_card_view, parent, false);
            vh = new ViewHolder();
            vh.f1 = convertView.findViewById(R.id.card_1);
            vh.f2 = convertView.findViewById(R.id.card_2);
            vh.f4 = convertView.findViewById(R.id.card_4);
            vh.go = convertView.findViewById(R.id.card_go);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.f1.setText(fLine.get(position).getfName());
        vh.f2.setText(fLine.get(position).getfDesc());
        vh.f4.setText(context.getResources().getString(R.string.best_score_time) + " " + fLine.get(position).getfScore());
        vh.go.setTag(position);
        return convertView;
    }

    private static class ViewHolder {
        private TextView f1;
        private TextView f2;
        private TextView f4;
        private TextView go;
    }
}
