package com.andraganoid.gameofmath.Arcade;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.GameBoard;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Big;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.Operation.Tri;
import com.andraganoid.gameofmath.R;

import static com.andraganoid.gameofmath.Game.calc;
import static com.andraganoid.gameofmath.Game.mathBase;
import static com.andraganoid.gameofmath.Game.task;


public class ArcadeBoard extends GameBoard {

    private long startTime;
    private Handler handler = new Handler();
    TextView sw, start;
    Long timeInMillis;
    // ArrayList<Long> top = new ArrayList<>();
//    ArrayList<Integer> prog = new ArrayList<>();


    @Override
    public void prepareTask() {

        keyboard.setVisibility(View.VISIBLE);
        //   (findViewById(R.id.game_board_scores)).setVisibility(View.GONE);
        typed.setVisibility(View.VISIBLE);
        typed.setClickable(false);
        formula.setClickable(false);

        (findViewById(R.id.arcade_bar)).setVisibility(View.VISIBLE);
        sw = findViewById(R.id.arcade_timer);
        start = findViewById(R.id.arcade_start);


        for (int i = 0; i < 10; i++) {
            switch (calc.levelNames.get(calc.gameKind)) {
                case "X + X":
                case "XX + X":
                case "XX + XX":

                    calc.arcadeList[i] = new Add();
                    break;

                case "X - X":
                case "XX - X":
                case "XX - XX":

                    calc.arcadeList[i] = new Sub();
                    break;

                case "X × X":

                    calc.arcadeList[i] = new Mul();
                    break;

                case "XX ÷ X":

                    calc.arcadeList[i] = new Div();
                    break;

                case "X × X + X":

                    calc.arcadeList[i] = new Big();
                    break;

                default:
                    calc.arcadeList[i] = new Tri();

            }
        }


        start.setVisibility(View.VISIBLE);
        start.setText("3");


        //  isEnd=true;
        intro = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
                if (l < 4000) {
                    start.setText(String.valueOf(l / 1000));
                }
            }

            @Override
            public void onFinish() {
                // isEnd=false;
                start.setVisibility(View.GONE);
                showTask();
            }
        };
        intro.start();


        //   showTask();

    }

    @Override
    public void showTask() {
        task = calc.arcadeList[calc.gameLevel - 1];
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


        ((TextView) findViewById(R.id.arcade_progress)).setText(progress);
        calc.gameLevel++;
        if (calc.gameLevel > calc.getHowManyTasks()) {
            handler.removeCallbacks(stopwatch);
            // formula.setText("Finish!");
            timeInMillis += badAnswers * 60 * 1000;
            sw.setText(calc.showTime(timeInMillis));
            //  typed.setText(Arcade.showTime(timeInMillis));
            // Toast.makeText(this, String.valueOf(Arcade.showTime(timeInMillis)), Toast.LENGTH_SHORT).show();


            if (timeInMillis <= calc.highScore || calc.highScore == 0) {
                mathBase.saveArcadeResult(calc.levelNames.get(calc.gameKind), timeInMillis);
                formula.setText("NEW BEST TIME");
                startAnimation(formula, 5);
                goFire();
            } else {
                formula.setText("GAME OVER!");
            }


            typed.setText(calc.showTime(timeInMillis));

            typed.setClickable(true);
            formula.setClickable(true);

            keyboard.setVisibility(View.INVISIBLE);


        } else {
            showTask();
        }
    }

    public void goNext(View v) {

        if (calc.gameLevel > calc.getHowManyTasks()) {
            Toast.makeText(this, "GAME OVER!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ArcadeSettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

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
        handler.removeCallbacks(stopwatch);
        if (goMain) {
            boardIntent = new Intent(this, Game.class);

        } else {
            boardIntent = new Intent(this, ArcadeSettings.class);

        }
        boardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(boardIntent);
        finish();
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
        Toast.makeText(this, "ARCADE HELP", Toast.LENGTH_LONG).show();
    }


}

