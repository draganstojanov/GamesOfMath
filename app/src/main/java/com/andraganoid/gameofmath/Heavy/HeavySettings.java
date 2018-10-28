package com.andraganoid.gameofmath.Heavy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.calc;


public class HeavySettings extends AppCompatActivity implements View.OnClickListener {
    Intent intent;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heavy_settings);

        // findViewById(R.id.heavy_set_lay).setBackground(new BitmapDrawable(getResources(), background));

        calc = new Heavy(Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels)));

        ((TextView) findViewById(R.id.heavy_10_1)).setText(calc.levelNames.get(0).toUpperCase().replace("_", " "));
        ((TextView) findViewById(R.id.heavy_100_1)).setText(calc.levelNames.get(1).toUpperCase().replace("_", " "));

        ((TextView) findViewById(R.id.heavy_10_2)).setText(Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description)).get(0));
        ((TextView) findViewById(R.id.heavy_100_2)).setText(Arrays.asList(getResources().getStringArray(R.array.heavy_calc_levels_description)).get(1));

        String a = getResources().getString(R.string.best_result) + String.valueOf(calc.scoreMap.get(calc.levelNames.get(0)));
        ((TextView) findViewById(R.id.heavy_10_3)).setText(a);
        a = getResources().getString(R.string.best_result) + String.valueOf(calc.scoreMap.get(calc.levelNames.get(1)));
        ((TextView) findViewById(R.id.heavy_100_3)).setText(a);

        findViewById(R.id.heavy_10).setOnClickListener(this);
        findViewById(R.id.heavy_100).setOnClickListener(this);

    }

    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        Toast.makeText(this, "HEAVY CALC SETTINGS HELP", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        intent = new Intent(this, HeavyBoard.class);


        switch (v.getId()) {

            case R.id.heavy_10:
                calc.gameKind = 10;
                calc.secondsForTask = 120;
                break;

            case R.id.heavy_100:
                calc.gameKind = 100;
                calc.secondsForTask = 180;
                break;

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
