package com.andraganoid.gameofmath.Fast;
/*

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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



public class FastAdapterOLD extends RecyclerView.Adapter<FastAdapterOLD.FastViewHolder> {
   // private Context mContext;
    private List<String>desc;

    public FastAdapterOLD(List<String> fastLevels, List<String>desc ) {
       // mContext = context;
        this.desc=desc;
        calc.scoreMap.clear();
      */
/*  for (int i = 0; i < fastLevels.size(); i++) {

            calc.scoreMap.put(fastLevels.get(i), 0l);
        }*//*


//        calc.scoreMap.putAll(MathBase.getInstance().getFastHiScores());
        calc.scoreMap.putAll(MathBase.getInstance().getHighScores(fastLevels));
    }


    @Override
    public FastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fast_card_view, parent, false);

        return new FastViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final FastViewHolder holder, int position) {

        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calc.gameKind = holder.getAdapterPosition();

//                String[] s = Arrays.asList(mContext.getResources().getStringArray(R.array.fast_calc_levels_description)).get(holder.getAdapterPosition()).split(" ");
                String[] s = desc.get(holder.getAdapterPosition()).split(" ");

                String g = "";
                for (int i = 0; i < s.length; i++) {
                    g += s[i] + "~";
                }

                calc.setOperationTypeByIndex(0, Arrays.asList(opSign).indexOf(s[1]));


                if (s.length == 3) {
                    calc.setOperandMinVal((int) Math.pow(10, s[0].length() - 1), (int) Math.pow(10, s[2].length() - 1));
                    calc.setOperandMaxVal((int) Math.pow(10, s[0].length()) - 1, (int) Math.pow(10, s[2].length()) - 1);
                } else {
                    calc.setOperationTypeByIndex(1, Arrays.asList(opSign).indexOf(s[3]));
                    calc.setOperandMinVal((int) Math.pow(10, s[0].length() - 1), (int) Math.pow(10, s[2].length() - 1), (int) Math.pow(10, s[4].length() - 1));
                    calc.setOperandMaxVal((int) Math.pow(10, s[0].length()) - 1, (int) Math.pow(10, s[2].length()) - 1, (int) Math.pow(10, s[4].length()) - 1);
                }


                calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind));

                Intent intent = new Intent(v.getContext(), FastBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);


                //   Log.i("math_CLICK_2",String.valueOf(holder.getAdapterPosition()));

            }
        });

        holder.lvl.setText(String.valueOf(calc.levelNames.get(position).toUpperCase().replace("_", " ")));
//        holder.lvl2.setText(Arrays.asList(mContext.getResources().getStringArray(R.array.fast_calc_levels_description)).get(position));
        holder.lvl2.setText(desc.get(position));

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




    public static class FastViewHolder extends RecyclerView.ViewHolder {

        TextView lvl, lvl2, hiScore;
        public View clickView;

        public FastViewHolder(View itemView) {
            super(itemView);
            clickView = itemView;
            this.lvl = itemView.findViewById(R.id.fast_1);
            this.lvl2 = itemView.findViewById(R.id.fast_2);
            this.hiScore = itemView.findViewById(R.id.fast_4);
        }
    }
}*/
