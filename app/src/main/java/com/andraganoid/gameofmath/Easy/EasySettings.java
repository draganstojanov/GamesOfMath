package com.andraganoid.gameofmath.Easy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.MathBase;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Game.calc;
import static com.andraganoid.gameofmath.Game.mathBase;


public class EasySettings extends AppCompatActivity {
    Intent intent;
    final int TIME_2 = 30;
    final int TIME_3 = 45;
    final int TIME_4 = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_settings);

        //    findViewById(R.id.lite_set_lay).setBackground(new BitmapDrawable(getResources(), background));

        intent = new Intent(this, EasyBoard.class);
        mathBase=new MathBase(this);
        calc = new Easy( Arrays.asList(getResources().getStringArray(R.array.easy_levels)));


//        calc.scoreMap.clear();
//        for (int i = 0; i < calc.levelNames.size(); i++) {
//
//            calc.scoreMap.put(calc.levelNames.get(i), 0l);
//            Log.i("score_ehs_scoreMap", String.valueOf(calc.scoreMap));
//        }
//
//        calc.scoreMap.putAll(mathBase.getEasyHiScores());
//        Log.i("score_ehs_scoreMap", String.valueOf(calc.scoreMap));

        ((TextView) findViewById(R.id.easy_br_2)).setText("Best Result: " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(0))));
        ((TextView) findViewById(R.id.easy_br_3)).setText("Best Result: " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(1))));
        ((TextView) findViewById(R.id.easy_br_4)).setText("Best Result: " + String.valueOf(calc.scoreMap.get(calc.levelNames.get(2))));
    }

    public void startLite2(View v) {
        calc.gameKind = 0;
        calc.secondsForTask = TIME_2;
        calc.setHowManyOperands(2);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void startLite3(View v) {
        calc.gameKind = 1;
        calc.secondsForTask = TIME_3;
        calc.setHowManyOperands(3);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void startLite4(View v) {
        calc.gameKind = 2;
        calc.secondsForTask = TIME_4;
        calc.setHowManyOperands(4);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHome(View v) {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHelp(View v) {
        Toast.makeText(this, "EASY CALC SETTINGS HELP", Toast.LENGTH_LONG).show();
    }
}
