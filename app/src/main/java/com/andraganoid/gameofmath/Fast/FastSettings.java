package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andraganoid.gameofmath.Operation.Calc.opSign;
import static com.andraganoid.gameofmath.Game.calc;


public class FastSettings extends AppCompatActivity {

    //    RecyclerView rv;
//    RecyclerView.Adapter fastAdapter;
//    RecyclerView.LayoutManager FastLayoutManager;
    private List<FastData> adFast = new ArrayList<FastData>();
    FastAdapter fAdapter;
    private AdView adViewBottomFast;
    //  private AdView adViewBottomFast;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adViewBottomFast.destroy();
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        //   fastAdapter.notifyDataSetChanged();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_settings);

        adViewBottomFast = findViewById(R.id.add_view_bottom_fast);
        adViewBottomFast.loadAd(new AdRequest.Builder().build());
        calc = new Fast(Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels)));


//        for (int i = 0; i < calc.levelNames.size(); i++) {
//            calc.scoreMap.put(calc.levelNames.get(i), 0l);
//        }
        calc.scoreMap.putAll(MathBase.getInstance().getHighScores(calc.levelNames));

        adFast.clear();

        for (int j = 0; j < calc.levelNames.size(); j++) {
            adFast.add(new FastData
                    (getString(R.string.fast_calc),
                            j+1,
                            Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description)).get(j),
                            calc.scoreMap.get(calc.levelNames.get(j))));
        }

        fAdapter = new FastAdapter(this, adFast);
        ((ListView) findViewById(R.id.fast_list_view)).setAdapter(fAdapter);

    }


    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.help_title)).setText(getString(R.string.fast_calc));
        ((TextView) findViewById(R.id.help_text)).setText(getString(R.string.help_fast_calc));
        findViewById(R.id.fast_set_lay).setClickable(false);

    }

    public void goCloseHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.GONE);
    }

    public void goFastPlay(View v) {
        calc.gameKind = (int) v.getTag();

//                String[] s = Arrays.asList(mContext.getResources().getStringArray(R.array.fast_calc_levels_description)).get(holder.getAdapterPosition()).split(" ");
        String[] s = Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description)).get(calc.gameKind).split(" ");

//        String g = "";
//        for (int i = 0; i < s.length; i++) {
//            g += s[i] + "~";
//        }

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
    }

    public void goFastGlobalHighScores(View v) {
        Toast.makeText(this, "HIGH SCORE " + String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
    }


}
