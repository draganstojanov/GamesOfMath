package com.andraganoid.gameofmath.HighScores;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andraganoid.gameofmath.R;

import java.util.List;

public class HighScoresTableAdapter extends RecyclerView.Adapter <HighScoresTableAdapter.HSTViewHolder> {
    private final List <Score> sList;
    private String res;
    private final long lastScoreId;

    public static class HSTViewHolder extends RecyclerView.ViewHolder {
        private final TextView pos;
        private final TextView result;
        private final TextView date;

        public HSTViewHolder(View itemView) {
            super(itemView);
            pos = itemView.findViewById(R.id.hs_pos);
            result = itemView.findViewById(R.id.hs_result);
            date = itemView.findViewById(R.id.hs_date);
        }
    }

    public HighScoresTableAdapter(List <Score> sList, long lastScoreId) {
        this.sList = sList;
        this.lastScoreId = lastScoreId;
    }

    @NonNull
    @Override
    public HighScoresTableAdapter.HSTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout itemView = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_row, parent, false);
        return new HSTViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HSTViewHolder holder, int position) {
        String pos= position + 1 + ".";
        holder.pos.setText(pos);
        Score score = sList.get(position);
        switch (score.getScoreType()) {
            case Score.SCORE_TYPE_POINTS:
                res = String.valueOf((score.getScorePoints()));
                break;
            case Score.SCORE_TYPE_TIME:
                res = score.getScoreTimeString();
                break;
        }
        holder.result.setText(res);
        holder.date.setText(score.getScoreDate());
        if ((int) lastScoreId == score.getId()) {
            int c = holder.itemView.getContext().getResources().getColor(R.color.text1);
            holder.pos.setTextColor(c);
            holder.result.setTextColor(c);
            holder.date.setTextColor(c);
        } else {
            int cc = holder.itemView.getContext().getResources().getColor(R.color.base);
            holder.pos.setTextColor(cc);
            holder.result.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.info));
            holder.date.setTextColor(cc);
        }
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }
}