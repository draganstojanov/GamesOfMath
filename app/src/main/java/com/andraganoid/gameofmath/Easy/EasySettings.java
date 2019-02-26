package com.andraganoid.gameofmath.Easy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.HighScores.Score;
import com.andraganoid.gameofmath.DataBase.ScoreCallback;
import com.andraganoid.gameofmath.DataBase.ScoreRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.HighScores.Level;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.Game.calc;


public class EasySettings extends AppCompatActivity {
    private Intent intent;
    private final int TIME_2 = 30;
    private final int TIME_3 = 45;
    private final int TIME_4 = 60;
    private AdView adViewBottomEasy;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adViewBottomEasy.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_settings);
        adViewBottomEasy = findViewById(R.id.add_view_bottom_easy);
        adViewBottomEasy.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        calc = new Easy();
        calc.level = new Level(Level.EASY_CALC,
                getString(R.string.easy_calc),
                Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels_description)));
        for (String lvl : calc.level.getLevelName()) {
            new ScoreRepository(getApplicationContext()).getBestPoints(lvl, sc);
        }
    }

    ScoreCallback sc = new ScoreCallback() {
        @Override
        public void bestScore(final Score scr) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (calc.level.setBestResultItem(scr.getLevelName(), scr)) {
                        initEasy();
                    }
                    ;
                }
            });
        }
    };

    private void initEasy() {

        ((TextView) findViewById(R.id.easy_2_1)).setText(calc.level.getLevelScreenNameItem(0));
        ((TextView) findViewById(R.id.easy_3_1)).setText(calc.level.getLevelScreenNameItem(1));
        ((TextView) findViewById(R.id.easy_4_1)).setText(calc.level.getLevelScreenNameItem(2));
        ((TextView) findViewById(R.id.easy_2_2)).setText(calc.level.getLevelDescItem(0));
        ((TextView) findViewById(R.id.easy_3_2)).setText(calc.level.getLevelDescItem(1));
        ((TextView) findViewById(R.id.easy_4_2)).setText(calc.level.getLevelDescItem(2));
        String a = getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.level.getBestResultItem(calc.level.getLevelName().get(0)).getScorePoints());
        ((TextView) findViewById(R.id.easy_2_3)).setText(a);
        a = getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.level.getBestResultItem(calc.level.getLevelName().get(1)).getScorePoints());
        ((TextView) findViewById(R.id.easy_3_3)).setText(a);
        a = getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.level.getBestResultItem(calc.level.getLevelName().get(2)).getScorePoints());
        ((TextView) findViewById(R.id.easy_4_3)).setText(a);
    }

    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goPlayGameEasy(View v) {
        v.setClickable(false);
        intent = new Intent(this, EasyBoard.class);

        switch (v.getId()) {
            case R.id.easy_go_2:
                calc.gameKind = 0;
                calc.secondsForTask = TIME_2;
                calc.setHowManyOperands(2);
                break;
            case R.id.easy_go_3:
                calc.gameKind = 1;
                calc.secondsForTask = TIME_3;
                calc.setHowManyOperands(3);
                break;
            case R.id.easy_go_4:
                calc.gameKind = 2;
                calc.secondsForTask = TIME_4;
                calc.setHowManyOperands(4);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.help_title)).setText(getString(R.string.easy_calc));
        ((TextView) findViewById(R.id.help_text)).setText(getString(R.string.help_easy_calc));
        findViewById(R.id.easy_set_lay).setClickable(false);
    }

    public void goCloseHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.GONE);
    }
}
