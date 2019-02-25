package com.andraganoid.gameofmath.Heavy;

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


public class HeavySettings extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private AdView adViewBottomHeavy;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adViewBottomHeavy.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heavy_settings);
        adViewBottomHeavy = findViewById(R.id.add_view_bottom_heavy);
        adViewBottomHeavy.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        calc = new Heavy();
        calc.level = new Level(Level.HEAVY_CALC,
                getString(R.string.heavy_calc),
                Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description)));
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
                        initHeavy();
                    }
                    ;
                }
            });
        }
    };

    private void initHeavy() {
        ((TextView) findViewById(R.id.heavy_10_1)).setText(calc.level.getLevelScreenNameItem(0));
        ((TextView) findViewById(R.id.heavy_100_1)).setText(calc.level.getLevelScreenNameItem(1));
        ((TextView) findViewById(R.id.heavy_10_2)).setText(calc.level.getLevelDescItem(0));
        ((TextView) findViewById(R.id.heavy_100_2)).setText(calc.level.getLevelDescItem(1));
        String a = getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.level.getBestResultItem(calc.level.getLevelName().get(0)).getScorePoints());
        ((TextView) findViewById(R.id.heavy_10_3)).setText(a);
        a = getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.level.getBestResultItem(calc.level.getLevelName().get(1)).getScorePoints());
        ((TextView) findViewById(R.id.heavy_100_3)).setText(a);
        findViewById(R.id.heavy_go_10).setOnClickListener(this);
        findViewById(R.id.heavy_go_100).setOnClickListener(this);
    }

    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        intent = new Intent(this, HeavyBoard.class);
        switch (v.getId()) {
            case R.id.heavy_go_10:
                calc.gameKind = 10;
                calc.secondsForTask = 120;
                break;
            case R.id.heavy_go_100:
                calc.gameKind = 100;
                calc.secondsForTask = 180;
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.help_title)).setText(getString(R.string.heavy_calc));
        ((TextView) findViewById(R.id.help_text)).setText(getString(R.string.help_heavy_calc));
        findViewById(R.id.heavy_set_lay).setClickable(false);
    }

    public void goCloseHelp(View v) {
        findViewById(R.id.help_layout).setVisibility(View.GONE);
    }
}
