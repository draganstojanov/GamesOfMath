package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Game.GameBoard;
import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Big;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.Operation.Tri;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;

import static com.andraganoid.gameofmath.Misc.MathSounds.LOST_LIFE;
import static com.andraganoid.gameofmath.Misc.MathSounds.START;


public class FastBoard extends GameBoard {

    private long startTime;
    private Handler handler = new Handler();
    TextView sw, start;
    Long timeInMillis;

    @Override
    public void prepareTask() {
        Toast.makeText(this, "PREPARE TASK", Toast.LENGTH_SHORT).show();


        goMain = true;
        keyboard.setVisibility(View.VISIBLE);
        typed.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.GONE);
        pauseBtn.setClickable(true);
        // typed.setClickable(false);
        //  formula.setClickable(false);
        board.setClickable(false);

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
                Log.d("intro", String.valueOf(l));
                if (l < 4000) {
                    play(START);
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
                play(LOST_LIFE);
            }

            nextBtn.setVisibility(View.VISIBLE);
            pauseBtn.setClickable(false);
            typed.setText(calc.showTime(timeInMillis));

//            typed.setClickable(true);
//            formula.setClickable(true);
            // formula.setClickable(true);

            keyboard.setVisibility(View.INVISIBLE);


        } else {
            showTask();
        }
    }

    @Override
    public void goNext(View v) {

        board.setClickable(false);
        if (fireTimer != null) {
            fireTimer.cancel();
        }
        showFullscreenAd();
        (findViewById(R.id.again_or_leaderboard)).setVisibility(View.VISIBLE);

    }

    public void goLeaderboards(View v) {

        showHighScoresTable(calc.levelNames.get(calc.gameKind));

    }

    private void fastCheckLeaderboard() {

        // xxxx vidi da li ima rezultat za lb


        // again or backtomenu?


        (findViewById(R.id.again_or_leaderboard)).setVisibility(View.VISIBLE);


//
//        goMain = false;
//        finish();


    }

    public void goAgain(View v) {

        calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind));
        calc.gameLevel = 1;
        adIsShowing = true;
        recreate();
//        Intent intent = new Intent(this, FastBoard.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);


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
        soundState();

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


}

