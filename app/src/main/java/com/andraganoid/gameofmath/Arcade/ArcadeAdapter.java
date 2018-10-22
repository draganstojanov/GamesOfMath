package com.andraganoid.gameofmath.Arcade;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andraganoid.gameofmath.MathBase;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;
import java.util.List;

import static com.andraganoid.gameofmath.Calc.opSign;
import static com.andraganoid.gameofmath.Game.calc;
//import static com.andraganoid.gameofmath.Game.mathBase;


public class ArcadeAdapter extends RecyclerView.Adapter<ArcadeAdapter.ArcadeViewHolder> implements View.OnClickListener {


    public ArcadeAdapter(List<String> arcadeLevels,Context context) {
        calc.scoreMap.clear();
        for (int i = 0; i < arcadeLevels.size(); i++) {

            calc.scoreMap.put(arcadeLevels.get(i), 0l);
        }

        calc.scoreMap.putAll(MathBase.getInstance().getArcadeHiScores());
    }


    @Override
    public ArcadeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arcade_card_view, parent, false);

        return new ArcadeViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final ArcadeViewHolder holder, int position) {

        Log.i("math_CLICK_2", String.valueOf(holder.getAdapterPosition()));
        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  calc = new Arcade();
                calc.gameKind = holder.getAdapterPosition();


                String[] s =calc.levelNames.get(holder.getAdapterPosition()).split(" ");
                // Toast.makeText(this, String.valueOf(s.length), Toast.LENGTH_SHORT).show();


                calc.setOperationTypeByIndex(0, Arrays.asList(opSign).indexOf(s[1]));

                if (s.length == 3) {
                    calc.setOperandMinVal((int) Math.pow(10, s[0].length() - 1), (int) Math.pow(10, s[2].length() - 1));
                    calc.setOperandMaxVal((int) Math.pow(10, s[0].length()) - 1, (int) Math.pow(10, s[2].length()) - 1);
                } else {
                    calc.setOperationTypeByIndex(1, Arrays.asList(opSign).indexOf(s[3]));
                    calc.setOperandMinVal((int) Math.pow(10, s[0].length() - 1), (int) Math.pow(10, s[2].length() - 1), (int) Math.pow(10, s[4].length() - 1));
                    calc.setOperandMaxVal((int) Math.pow(10, s[0].length()) - 1, (int) Math.pow(10, s[2].length()) - 1, (int) Math.pow(10, s[4].length()) - 1);
                }

                Log.i("math_THIS", String.valueOf(this));


                calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind));

                Intent intent = new Intent(v.getContext(), ArcadeBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);


                //   Log.i("math_CLICK_2",String.valueOf(holder.getAdapterPosition()));

            }
        });

        holder.lvl.setText(String.valueOf(calc.levelNames.get(position)));

        if (calc.scoreMap.get(calc.levelNames.get(position)) == 0) {
            holder.hiScore.setText("No result");
        } else {
          //  holder.hiScore.setText(String.valueOf(l));
            holder.hiScore.setText(String.valueOf(calc.showTime(calc.scoreMap.get(calc.levelNames.get(position)))));
        }
    }


    @Override
    public int getItemCount() {
        return calc.scoreMap.size();
    }

    @Override
    public void onClick(View view) {

    }


    public static class ArcadeViewHolder extends RecyclerView.ViewHolder {

        TextView lvl;
        TextView hiScore;
        public View clickView;

        public ArcadeViewHolder(View itemView) {
            super(itemView);
            clickView = itemView;
            this.lvl =  itemView.findViewById(R.id.alv_level);
            this.hiScore =  itemView.findViewById(R.id.alv_hiscore);
        }
    }
}