package com.andraganoid.gameofmath.HighScores;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andraganoid.gameofmath.DataBase.Score;
import com.andraganoid.gameofmath.R;

import java.util.List;

public class HighScoresTableAdapter extends RecyclerView.Adapter <HighScoresTableAdapter.HSTViewHolder> {
    private List <Score> sList;
    private String res;
    private Score score;

    public static class HSTViewHolder extends RecyclerView.ViewHolder {
        private TextView pos;
        private TextView result;
        private TextView date;

        public HSTViewHolder(View itemView) {
            super(itemView);
            pos = itemView.findViewById(R.id.hs_pos);
            result = itemView.findViewById(R.id.hs_result);
            date = itemView.findViewById(R.id.hs_date);

        }
    }

    public HighScoresTableAdapter(List <Score> sList) {
        this.sList = sList;
    }


    @Override
    public HighScoresTableAdapter.HSTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ConstraintLayout itemView = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_row, parent, false);

        HSTViewHolder vh = new HSTViewHolder(itemView);
        return vh;
    }


    @Override
    public void onBindViewHolder(HSTViewHolder holder, int position) {

        holder.pos.setText(String.valueOf(position + 1) + ".");

        score = sList.get(position);
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

    }

    @Override
    public int getItemCount() {
        return sList.size();
    }
}