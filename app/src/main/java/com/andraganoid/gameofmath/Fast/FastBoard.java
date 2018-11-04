package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.GameBoard;
import com.andraganoid.gameofmath.MathBase;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Big;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.Operation.Tri;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;

//import static com.andraganoid.gameofmath.Game.mathBase;


public class FastBoard extends GameBoard {

    private long startTime;
    private Handler handler = new Handler();
    TextView sw, start;
    Long timeInMillis;
    // ArrayList<Long> top = new ArrayList<>();
//    ArrayList<Integer> prog = new ArrayList<>();


    @Override
    public void prepareTask() {
        goMain = true;
        keyboard.setVisibility(View.VISIBLE);
        //   (findViewById(R.id.game_board_scores)).setVisibility(View.GONE);
        typed.setVisibility(View.VISIBLE);
        typed.setClickable(false);
        formula.setClickable(false);

        (findViewById(R.id.fast_bar)).setVisibility(View.VISIBLE);
        sw = findViewById(R.id.fast_timer);
        start = findViewById(R.id.fast_start);


        for (int i = 0; i < 10; i++) {

            switch (Arrays.asList(getResources().getStringArray(R.array.fast_calc_levels_description)).get(calc.gameKind)) {

                case "X + X":
                case "XX + X":
                case "XX + XX":

                    calc.fastList[i] = new Add();
                    break;

                case "X - X":
                case "XX - X":
                case "XX - XX":

                    calc.fastList[i] = new Sub();
                    break;

                case "X × X":

                    calc.fastList[i] = new Mul();
                    break;

                case "XX ÷ X":

                    calc.fastList[i] = new Div();
                    break;

                case "X × X + X":

                    calc.fastList[i] = new Big();
                    break;

                default:
                    calc.fastList[i] = new Tri();

            }
        }


        start.setVisibility(View.VISIBLE);
        start.setText("3");


        isEnd = true;
        goMain = false;
        intro = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
                if (l < 4000) {
                    start.setText(String.valueOf(l / 1000));
                }
            }

            @Override
            public void onFinish() {
                isEnd = false;
                goMain = true;
                start.setVisibility(View.GONE);
                showTask();
            }
        };
        intro.start();


    }

    @Override
    public void showTask() {
        task = calc.fastList[calc.gameLevel - 1];
        typedResult = "";
        typed.setText("");
        formula.setText(task.getFormula());

        if (calc.gameLevel == 1) {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(stopwatch, 0);
        }
    }


    @Override
    public void next(String s) {


        ((TextView) findViewById(R.id.fast_progress)).setText(progress);
        calc.gameLevel++;
        if (calc.gameLevel > calc.getHowManyTasks()) {
            handler.removeCallbacks(stopwatch);
            // formula.setText("Finish!");
            timeInMillis += badAnswers * 60 * 1000;
            sw.setText(calc.showTime(timeInMillis));
            //  typed.setText(Fast.showTime(timeInMillis));
            // Toast.makeText(this, String.valueOf(Fast.showTime(timeInMillis)), Toast.LENGTH_SHORT).show();


            if (timeInMillis <= calc.highScore || calc.highScore == 0) {
//                MathBase.getInstance().saveFastResult(calc.levelNames.get(calc.gameKind), timeInMillis);
                MathBase.getInstance().saveHighScore(calc.levelNames.get(calc.gameKind), timeInMillis);
                formula.setText(getString(R.string.new_high));
                startAnimation(formula, 5);
                goFire();
            } else {
                formula.setText(getString(R.string.game_over));
            }


            typed.setText(calc.showTime(timeInMillis));

            typed.setClickable(true);
            formula.setClickable(true);

            keyboard.setVisibility(View.INVISIBLE);


        } else {
            showTask();
        }
    }

    @Override
    public void goNext(View v) {
        if (calc.gameLevel > calc.getHowManyTasks()) {

            showFullscreenAd();
            //  goMain = false;
            // finish();

        } else {
            prepareTask();
        }
    }


    private Runnable stopwatch = new Runnable() {
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            sw.setText(calc.showTime(timeInMillis + badAnswers * 60 * 1000));
            handler.postDelayed(this, 0);
        }
    };


    public void goPause(View v) {
        //  if (!isEnd) {
        handler.removeCallbacks(stopwatch);
        findViewById(R.id.pause_dialog).setVisibility(View.VISIBLE);

        //   }
    }

    public void goResume(View v) {
        findViewById(R.id.pause_dialog).setVisibility(View.GONE);
        startTime = SystemClock.uptimeMillis() - timeInMillis;
        handler.postDelayed(stopwatch, 0);

    }


    @Override
    protected void onPause() {

        super.onPause();
        if (!adIsShowing) {

            if (intro != null) {
                intro.cancel();
            }
            handler.removeCallbacks(stopwatch);
            if (goMain) {
                boardIntent = new Intent(this, Game.class);

            } else {
                boardIntent = new Intent(this, FastSettings.class);

            }
            boardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(boardIntent);
            finish();

        }
    }

    public void goHome(View v) {
        goMain = true;
        finish();
    }

    public void goBack(View v) {
        goMain = false;
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain = false;
    }


    public void goHelp(View v) {
        Toast.makeText(this, "FAST CALC HELP", Toast.LENGTH_LONG).show();
    }


}

