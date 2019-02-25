package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.andraganoid.gameofmath.HighScores.Score;
import com.andraganoid.gameofmath.DataBase.ScoreCallback;
import com.andraganoid.gameofmath.DataBase.ScoreRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.HighScores.Level;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andraganoid.gameofmath.Operation.Calc.opSign;
import static com.andraganoid.gameofmath.Game.Game.calc;


public class FastSettings extends AppCompatActivity {
    private List <FastData> adFast = new ArrayList <>();
    FastAdapter fAdapter;
    private AdView adViewBottomFast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_settings);
        adViewBottomFast = findViewById(R.id.add_view_bottom_fast);
        adViewBottomFast.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        calc = new Fast();
        calc.level = new Level(Level.FAST_CALC,
                getString(R.string.fast_calc),
                Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description)));
        for (String lvl : calc.level.getLevelName()) {
            new ScoreRepository(getApplicationContext()).getBestTime(lvl, sc);
        }
    }

    ScoreCallback sc = new ScoreCallback() {
        @Override
        public void bestScore(final Score scr) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (calc.level.setBestResultItem(scr.getLevelName(), scr)) {
                        initFast();
                    }
                    ;
                }
            });
        }
    };


    private void initFast() {
        adFast.clear();
        for (int i = 0; i < 14; i++) {
            adFast.add(new FastData(
                    calc.level.getScreenLevelNameItem(i),
                    calc.level.getLevelDescItem(i),
                    calc.level.getBestResultItem(calc.level.getLevelName().get(i)).getScoreTimeString()));
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

    public void goPlayGameFast(View v) {
        calc.gameKind = (int) v.getTag();
        String[] s = Arrays.asList(getResources()
                .getStringArray(R.array.fast_calc_levels_description))
                .get(calc.gameKind).split(" ");
        calc.setOperationTypeByIndex(0, Arrays.asList(opSign).indexOf(s[1]));
        if (s.length == 3) {
            calc.setOperandMinVal((int) Math.pow(10, s[0].length() - 1), (int) Math.pow(10, s[2].length() - 1));
            calc.setOperandMaxVal((int) Math.pow(10, s[0].length()) - 1, (int) Math.pow(10, s[2].length()) - 1);
        } else {
            calc.setOperationTypeByIndex(1, Arrays.asList(opSign).indexOf(s[3]));
            calc.setOperandMinVal((int) Math.pow(10, s[0].length() - 1), (int) Math.pow(10, s[2].length() - 1), (int) Math.pow(10, s[4].length() - 1));
            calc.setOperandMaxVal((int) Math.pow(10, s[0].length()) - 1, (int) Math.pow(10, s[2].length()) - 1, (int) Math.pow(10, s[4].length()) - 1);
        }
        Intent intent = new Intent(v.getContext(), FastBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        v.getContext().startActivity(intent);
    }
}
