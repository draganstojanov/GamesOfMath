package com.andraganoid.gameofmath.Fast;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.HighScores.Score;
import com.andraganoid.gameofmath.DataBase.ScoreCallback;
import com.andraganoid.gameofmath.DataBase.ScoreListCallback;
import com.andraganoid.gameofmath.DataBase.ScoreRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Game.GameBoard;
import com.andraganoid.gameofmath.Misc.FullscreenCallback;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Big;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.Operation.Tri;
import com.andraganoid.gameofmath.R;

import java.util.Arrays;
import java.util.List;

import static com.andraganoid.gameofmath.Misc.Sounds.LOST_LIFE;
import static com.andraganoid.gameofmath.Misc.Sounds.START;


public class FastBoard extends GameBoard {

    private long startTime;
    private Handler handler = new Handler();
    TextView sw, start;
    Long timeInMillis;

    @Override
    protected void onResume() {
        super.onResume();
        new ScoreRepository(getApplicationContext()).getBestTime(calc.level.getLevelNameItem(calc.gameKind), scoreCallback);
    }

    @Override
    public void prepareTask() {
        goMain = true;
        keyboard.setVisibility(View.VISIBLE);
        typed.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.GONE);
        pauseBtn.setClickable(true);
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
        intro = new CountDownTimer(4000, 1000) {

            @Override
            public void onTick(long l) {
                play(START);
                start.setText(String.valueOf(l / 1000));
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
            timeInMillis += badAnswers * 60 * 1000;
            sw.setText(Score.setScoreTimeStringFromMillis(timeInMillis));
            if (timeInMillis <= calc.highScore.getScoreMillis() || calc.highScore.getScoreMillis() == 0) {
                formula.setText(getString(R.string.new_high));
                startAnimation(formula, 5);
                goFire();
            } else {
                formula.setText(getString(R.string.game_over));
                play(LOST_LIFE);
            }
            nextBtn.setVisibility(View.VISIBLE);
            pauseBtn.setClickable(false);
            typed.setText(Score.setScoreTimeStringFromMillis(timeInMillis));
            keyboard.setVisibility(View.INVISIBLE);
        } else {
            showTask();
        }
    }

    @Override
    public void goNext(View v) {
        showFullscreenAd(fc);
        board.setClickable(false);
        if (fireTimer != null) {
            fireTimer.cancel();
        }
    }

    FullscreenCallback fc = new FullscreenCallback() {
        @Override
        public void afterFullscreenAd() {
            turnTheScreenOff();
            (findViewById(R.id.highscore_table)).setVisibility(View.VISIBLE);
            (findViewById(R.id.three_btn)).setVisibility(View.VISIBLE);
            ((TextView) (findViewById(R.id.hs_name))).setText(calc.level.getScreenLevelNameItem(calc.gameKind));
            new ScoreRepository(getApplicationContext()).saveScore(new Score(calc.level.getLevelNameItem(calc.gameKind), timeInMillis), slc);
        }
    };


    public void goAgain(View v) {
        calc.gameLevel = 1;
        adIsShowing = true;
        recreate();
    }

    public void goMain(View v) {
        goMain = true;
        finish();
    }


    private Runnable stopwatch = new Runnable() {
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            sw.setText(Score.setScoreTimeStringFromMillis(timeInMillis + badAnswers * 60 * 1000));
            handler.postDelayed(this, 0);
        }
    };

    public void goPause(View v) {
        handler.removeCallbacks(stopwatch);
        findViewById(R.id.pause_dialog).setVisibility(View.VISIBLE);
        soundState();
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

    ScoreCallback scoreCallback = new ScoreCallback() {
        @Override
        public void bestScore(Score scr) {
            calc.highScore = scr;
        }
    };

    ScoreListCallback slc = new ScoreListCallback() {
        @Override
        public void scoreSaved(final List <Score> scoreList, String levelName, final long lastScoreId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setHighScoreTableAdapter(scoreList, lastScoreId);
                }
            });

        }

        @Override
        public void scoreList(List <Score> scoreList) {
        }
    };
}

