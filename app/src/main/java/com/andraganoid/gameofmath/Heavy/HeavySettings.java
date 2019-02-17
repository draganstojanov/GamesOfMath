package com.andraganoid.gameofmath.Heavy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.Game.calc;


public class HeavySettings extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
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

        // findViewById(R.id.heavy_set_lay).setBackground(new BitmapDrawable(getResources(), background));

        calc = new Heavy(Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels)));

        ((TextView) findViewById(R.id.heavy_10_1)).setText(getString(R.string.heavy_calc) + " 10");
        ((TextView) findViewById(R.id.heavy_100_1)).setText(getString(R.string.heavy_calc) + " 100");


        ((TextView) findViewById(R.id.heavy_10_2)).setText(Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description)).get(0));
        ((TextView) findViewById(R.id.heavy_100_2)).setText(Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description)).get(1));

        ((TextView) findViewById(R.id.heavy_10_3)).setText(getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(0))));
        ((TextView) findViewById(R.id.heavy_100_3)).setText(getResources().getString(R.string.best_score_time) + "  " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(1))));

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
