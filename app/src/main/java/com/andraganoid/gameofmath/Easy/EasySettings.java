package com.andraganoid.gameofmath.Easy;

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


public class EasySettings extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    final int TIME_2 = 30;
    final int TIME_3 = 45;
    final int TIME_4 = 60;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_settings);

        //    findViewById(R.id.lite_set_lay).setBackground(new BitmapDrawable(getResources(), background));

        calc = new Easy(Arrays.asList(getResources().getStringArray(R.array.easy_calc_levels)));

        ((TextView) findViewById(R.id.easy_br_2)).setText("Best Result: " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(0))));
        ((TextView) findViewById(R.id.easy_br_3)).setText("Best Result: " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(1))));
        ((TextView) findViewById(R.id.easy_br_4)).setText("Best Result: " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(2))));

        findViewById(R.id.easy_2).setOnClickListener(this);
        findViewById(R.id.easy_3).setOnClickListener(this);
        findViewById(R.id.easy_4).setOnClickListener(this);
    }


    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        Toast.makeText(this, "EASY CALC SETTINGS HELP", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        intent = new Intent(this, EasyBoard.class);


        switch (v.getId()) {

            case R.id.easy_2:
                calc.gameKind = 0;
                calc.secondsForTask = TIME_2;
                calc.setHowManyOperands(2);
                break;

            case R.id.easy_3:
                calc.gameKind = 1;
                calc.secondsForTask = TIME_3;
                calc.setHowManyOperands(3);
                break;

            case R.id.easy_4:
                calc.gameKind = 2;
                calc.secondsForTask = TIME_4;
                calc.setHowManyOperands(4);
                break;

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
