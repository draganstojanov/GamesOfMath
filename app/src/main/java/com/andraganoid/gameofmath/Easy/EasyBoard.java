package com.andraganoid.gameofmath.Easy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.DataBase.Bonus;
import com.andraganoid.gameofmath.DataBase.BonusCallback;
import com.andraganoid.gameofmath.DataBase.BonusRepository;
import com.andraganoid.gameofmath.HighScores.Score;
import com.andraganoid.gameofmath.DataBase.ScoreCallback;
import com.andraganoid.gameofmath.DataBase.ScoreListCallback;
import com.andraganoid.gameofmath.DataBase.ScoreRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Game.GamePlay;
import com.andraganoid.gameofmath.HighScores.Level;
import com.andraganoid.gameofmath.Misc.FullscreenCallback;
import com.andraganoid.gameofmath.Operation.Lit;
import com.andraganoid.gameofmath.R;


import java.util.ArrayList;
import java.util.List;

import static com.andraganoid.gameofmath.Misc.Sounds.GET_BONUS;
import static com.andraganoid.gameofmath.Misc.Sounds.LOST_LIFE;
import static com.andraganoid.gameofmath.Misc.Sounds.NO_BONUS;
import static com.andraganoid.gameofmath.Misc.Sounds.RIGHT_ANSWER;
import static com.andraganoid.gameofmath.Misc.Sounds.START;
import static com.andraganoid.gameofmath.Misc.Sounds.TIME_IS_OUT;
import static com.andraganoid.gameofmath.Misc.Sounds.USE_BONUS;
import static com.andraganoid.gameofmath.Operation.Task.eval;

public class EasyBoard extends GamePlay {

    private TextView lNum[], lOper[], lEr[];
    private ArrayList <Integer> easyFornulaArr = new ArrayList <>();
    private TextView lResult, lTarget, lErase, lTimer, skip, xtraTime, reset, start, eScore, formula;
    private boolean isNum;
    private int secondsLeft;
    private BonusRepository bonusRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_board);

        // new ScoreRepository(getApplicationContext()).getBestPoints(Level.EASY_CALC,scoreCallback);
        new ScoreRepository(getApplicationContext()).getBestPoints(calc.level.getLevelNameItem(calc.gameKind), scoreCallback);

        bonusRepository = new BonusRepository(getApplicationContext());
        bonusRepository.getBonusesForGame(Level.EASY_CALC, bonusCallback);

        // calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind));

        board = findViewById(R.id.easy_board_lay);
        start = findViewById(R.id.easy_start);
        lResult = findViewById(R.id.easy_result);
        lTarget = findViewById(R.id.easy_target);
        lErase = findViewById(R.id.easy_erase);
        lTimer = findViewById(R.id.easy_timer);
        skip = findViewById(R.id.easy_skip);
        xtraTime = findViewById(R.id.easy_xtra_time);
        reset = findViewById(R.id.easy_reset);
        eScore = findViewById(R.id.easy_score);
        formula = findViewById(R.id.easy_formula);

        lNum = new TextView[]
                {findViewById(R.id.easy_op_0),
                        findViewById(R.id.easy_op_1),
                        findViewById(R.id.easy_op_2),
                        findViewById(R.id.easy_op_3),
                        findViewById(R.id.easy_op_4),
                        findViewById(R.id.easy_op_5)};

        lOper = new TextView[]
                {findViewById(R.id.easy_s_0),
                        findViewById(R.id.easy_s_1),
                        findViewById(R.id.easy_s_2),
                        findViewById(R.id.easy_s_3)};

        lEr = new TextView[]
                {lErase};

        secondsLeft = calc.secondsForTask + 1;


        start.setVisibility(View.VISIBLE);
        start.setText("3");

        eScore.setText(calc.resetScore());
        isEnd = true;
        goMain = false;
        intro = new CountDownTimer(4000, 1000) {

            @Override
            public void onTick(long l) {

              //  if (l < 4000) {
                    play(START);
                    start.setText(String.valueOf(l / 1000));
               // }
            }

            @Override
            public void onFinish() {
                start.setVisibility(View.GONE);
                isEnd = false;
                goMain = true;
                runEasy();


            }
        };
        intro.start();


    }

    private void runEasy() {

        calc.gameLevel++;
        checkForBonusesEasy();

        board.setClickable(false);
        //  formula.setClickable(false);

        task = new Lit();

        eScore.setText(calc.easyScore("nextLvl"));


        easyFornulaArr.clear();
        lTarget.setText(String.valueOf(task.getResult()));


        for (int i = 0; i < 6; i++) {
            lNum[i].setText(String.valueOf(task.easyNumbers[i]));
            lNum[i].setVisibility(View.VISIBLE);
        }

        isNum = true;
        writeFormula();


        secondsLeft--;
        if (secondsLeft < 10) {
            secondsLeft = 10;
        }

        timerStart(secondsLeft);
    }


    private void timerStart(int s) {
        colorChange = true;
        cdt = new CountDownTimer(s * 1000 + 250, 500) {
            @Override
            public void onTick(long l) {
                calc.secondsRemain = (int) l / 1000;
                //   if (l >500) {
                lTimer.setTextColor(getResources().getColor(R.color.info));
                lTimer.setText(String.valueOf(l / 1000));

                if ((l / 1000) < 10) {
                    if (colorChange) {
                        lTimer.setTextColor(getResources().getColor(R.color.checked));
                        play(TIME_IS_OUT);
                    }
//
                    colorChange = !colorChange;
                }


                //  } else {
                // lTimer.setText("0");
                // easyGameOver();
                //   }

            }

            @Override
            public void onFinish() {
                lTimer.setTextColor(getResources().getColor(R.color.info))
                ;
                easyGameOver();
            }
        };
        cdt.start();
    }

    private void easyGameOver() {

        isEnd = true;

        if (calc.currentScore >= calc.highScore.getScorePoints() || calc.highScore.getScorePoints() == 0) {


//            MathBase.getInstance().saveHighScore(calc.levelNames.get(calc.gameKind), (long) (calc.currentScore));
            formula.setText(getString(R.string.new_high));
            startAnimation(formula, 5);
            goFire();
        } else {
            formula.setText(getString(R.string.game_over));
            play(LOST_LIFE);
        }
        //  formula.setClickable(true);
        board.setClickable(true);
    }

    private void writeFormula() {

        if (isNum) {
            prepare4click(lNum);
            unPrepare4click(lOper);
        } else {
            if (isMax()) {
                prepare4click(lOper);
            }
            unPrepare4click(lNum);
        }


        if (easyFornulaArr.size() == 0) {
            unPrepare4click(lEr);
        } else {
            prepare4click(lEr);
        }

        String s = " ";
        int ls = easyFornulaArr.size();
        for (int i = 0; i < ls; i++) {
            s += ((TextView) findViewById(easyFornulaArr.get(i))).getText().toString() + " ";
        }

        ((TextView) findViewById(R.id.easy_formula)).setText(s);
        if (ls == 2 || ls == 4 || ls == 6) {
            s = s.substring(0, s.length() - 2);
        }

        if (ls != 0) {

            lResult.setText(String.valueOf(eval(s)));
        } else {
            lResult.setText("");
        }


        if (task.getResult() == eval(s) && easyFornulaArr.size() > 2) {
            if (cdt != null) {
                cdt.cancel();
            }
            eScore.setText(calc.easyScore("submit", (int) ((1 + calc.gameKind / 5) * (((float) calc.secondsRemain / (float) secondsLeft * 10) * (100 + (float) calc.gameLevel) / 50))));

            play(RIGHT_ANSWER);
            runEasy();

        }

    }

    public void formulaRemove(View v) {
        if (!isEnd) {
            if (easyFornulaArr.size() > 0) {
                eScore.setText(calc.easyScore("clear"));
                (findViewById(easyFornulaArr.get(easyFornulaArr.size() - 1))).setVisibility(View.VISIBLE);
                easyFornulaArr.remove(easyFornulaArr.size() - 1);
                isNum = !isNum;
                writeFormula();

            }
        }
    }

    public void easyNum(View v) {

        if (!isEnd) {

            if (isNum && isMax()) {
                eScore.setText(calc.easyScore("click"));
                easyFornulaArr.add(v.getId());
                v.setVisibility(View.INVISIBLE);
                isNum = false;
                writeFormula();


            }
        }
    }


    public void easyOper(View v) {
        if (!isEnd) {
            if (!isNum && isMax()) {
                eScore.setText(calc.easyScore("click"));
                easyFornulaArr.add(v.getId());
                isNum = true;
                writeFormula();


            }
        }
    }

    private boolean isMax() {
        return (easyFornulaArr.size() < (2 * calc.getHowManyOperands() - 1));
    }


    private void prepare4click(TextView[] bv) {

        for (TextView tv : bv) {

            tv.setClickable(true);
            tv.setBackground(getResources().getDrawable(R.drawable.tv_stroke));
        }
    }


    private void unPrepare4click(TextView[] bv) {

        for (TextView tv : bv) {

            tv.setClickable(false);
            tv.setBackgroundColor(getResources().getColor(R.color.base));
        }


    }


    public void easyReset(View v) {
        if (!isEnd) {

            if (calc.easyResets.getValue() > 0) {

                eScore.setText(calc.easyScore("reset"));
                if (cdt != null) {
                    cdt.cancel();
                }
                secondsLeft = calc.secondsForTask + 1;
                // calc.easyResets = calc.setBonus(calc.EASY_RESETS, calc.easyResets - 1);
                bonusRepository.saveBonus(calc.easyResets, Bonus.DECREASE, bonusCallback);


//             setTimeResetText ();
//                play(USE_BONUS);
//                startAnimation(reset, 1);
//                runEasy();

            } else {
                play(NO_BONUS);
            }
        }
    }


    public void easySkip(View v) {
        if (!isEnd) {

            if (calc.easySkips.getValue() > 0) {
                eScore.setText(calc.easyScore("skip"));
                if (cdt != null) {
                    cdt.cancel();
                }

                // calc.easySkips = calc.setBonus(calc.EASY_SKIPS, calc.easySkips - 1);
                bonusRepository.saveBonus(calc.easySkips, Bonus.DECREASE, bonusCallback);

//                 setSkipText ();
//                play(USE_BONUS);
//                startAnimation(skip, 1);
//                runEasy();
            } else {
                play(NO_BONUS);
            }


        }
    }

    public void easyXtraTime(View v) {
        if (!isEnd) {
            if (calc.easyXtraTime.getValue() > 0) {
                eScore.setText(calc.easyScore("xtra"));
                if (cdt != null) {
                    cdt.cancel();
                }

                timerStart(calc.secondsRemain + 30);
                // calc.easyXtraTime = calc.setBonus(calc.EASY_XTRA_TIME, calc.easyXtraTime - 1);
                bonusRepository.saveBonus(calc.easyXtraTime, Bonus.DECREASE, bonusCallback);


//                setXtraTimeText ();
//                play(USE_BONUS);
//                startAnimation(xtraTime, 1);
            } else {
                play(NO_BONUS);
            }
        }
    }


    private void checkForBonusesEasy() {

        setSkipText();
        setXtraTimeText();
        setTimeResetText();

        if (calc.gameLevel % 20 == 0) {//add SKIP
            // calc.easySkips = calc.setBonus(calc.EASY_SKIPS, calc.easySkips + 1);
            bonusRepository.saveBonus(calc.easySkips, Bonus.INCREASE, bonusCallback);
//            setSkipText();
//            play(GET_BONUS);
//            startAnimation(skip, 1);

        }
        if (calc.gameLevel % 24 == 0) {//add XTRA
            // calc.easyXtraTime = calc.setBonus(calc.EASY_XTRA_TIME, calc.easyXtraTime + 1);
            bonusRepository.saveBonus(calc.easyXtraTime, Bonus.INCREASE, bonusCallback);
//            setXtraTimeText();
//            play(GET_BONUS);
//            startAnimation(xtraTime, 1);

        }
        if (calc.gameLevel % 33 == 0) {//add RESET
            // calc.easyResets = calc.setBonus(calc.EASY_RESETS, calc.easyResets + 1);
            bonusRepository.saveBonus(calc.easyResets, Bonus.INCREASE, bonusCallback);
//            setTimeResetText();
//            play(GET_BONUS);
//            startAnimation(reset, 1);
        }


    }


    public void goPause(View v) {
        if (!isEnd) {
            cdt.cancel();
            findViewById(R.id.pause_dialog).setVisibility(View.VISIBLE);
            soundState();
            // showPauseDialog();

        }
    }

    public void goResume(View v) {
        findViewById(R.id.pause_dialog).setVisibility(View.GONE);
        timerStart(calc.secondsRemain);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!adIsShowing) {

            if (intro != null) {
                intro.cancel();
            }
            if (cdt != null) {
                cdt.cancel();
            }

            if (goMain) {
                boardIntent = new Intent(this, Game.class);

            } else {
                boardIntent = new Intent(this, EasySettings.class);

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

    public void easyOver(View v) {
        showFullscreenAd(fc);
        board.setClickable(false);
        if (fireTimer != null) {
            fireTimer.cancel();
        }
        // calc.highScore.setScorePoints(calc.currentScore);
//        new ScoreRepository(getApplicationContext()).saveScore(calc.highScore,slc);

//        turnTheScreenOff();
//
//        (findViewById(R.id.highscore_table)).setVisibility(View.VISIBLE);
//        (findViewById(R.id.three_btn)).setVisibility(View.VISIBLE);
//        ((TextView) (findViewById(R.id.hs_name))).setText(calc.level.getScreenLevelNameItem(calc.gameKind));
//        new ScoreRepository(getApplicationContext()).saveScore(new Score(calc.level.getLevelNameItem(calc.gameKind), calc.currentScore), slc);
    }


    FullscreenCallback fc = new FullscreenCallback() {
        @Override
        public void afterFullscreenAd() {
            turnTheScreenOff();

            (findViewById(R.id.highscore_table)).setVisibility(View.VISIBLE);
            (findViewById(R.id.three_btn)).setVisibility(View.VISIBLE);
            ((TextView) (findViewById(R.id.hs_name))).setText(calc.level.getScreenLevelNameItem(calc.gameKind));
            new ScoreRepository(getApplicationContext()).saveScore(new Score(calc.level.getLevelNameItem(calc.gameKind), calc.currentScore), slc);
        }
    };

    public void goAgain(View v) {

        // calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind));
        calc.gameLevel = 1;
        adIsShowing = true;
        recreate();
//        Intent intent = new Intent(this, FastBoard.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }


    public void goMain(View v) {

        goMain = true;
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain = false;
    }


    private void setSkipText() {
        skip.setText(getString(R.string.skips) + String.valueOf(calc.easySkips.getValue()));
        if (calc.easySkips.getValue() == 0) {
            skip.setBackgroundColor(Color.TRANSPARENT);
        } else {
            skip.setBackgroundColor(getResources().getColor(R.color.info));
        }
    }

    private void setXtraTimeText() {
        xtraTime.setText(getString(R.string.xtra_time) + String.valueOf(calc.easyXtraTime.getValue()));
        if (calc.easyXtraTime.getValue() == 0) {
            xtraTime.setBackgroundColor(Color.TRANSPARENT);
        } else {
            xtraTime.setBackgroundColor(getResources().getColor(R.color.info));
        }
    }

    private void setTimeResetText() {
        reset.setText(getString(R.string.resets) + String.valueOf(calc.easyResets.getValue()));
        if (calc.easyResets.getValue() == 0) {
            reset.setBackgroundColor(Color.TRANSPARENT);
        } else {
            reset.setBackgroundColor(getResources().getColor(R.color.info));
        }
    }


    BonusCallback bonusCallback = new BonusCallback() {

        @Override
        public void easy(final Bonus bonus) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bonusHandler(bonus);
                }
            });

        }

        @Override
        public void heavy(Bonus bonus) {
            //  xxx
        }

        @Override
        public void game(List <Bonus> bonusesForGame) {
            for (Bonus bonus : bonusesForGame) {

                switch (bonus.getBonusName()) {
                    case Bonus.EASY_SKIPS:
                        calc.easySkips = bonus;
                        break;
                    case Bonus.EASY_XTRA_TIME:
                        calc.easyXtraTime = bonus;
                        break;
                    case Bonus.EASY_RESETS:
                        calc.easyResets = bonus;
                        break;
                }
            }


        }
    };


    private void bonusHandler(Bonus bonus) {

        View v = null;
        switch (bonus.getBonusName()) {
            case Bonus.EASY_SKIPS:
                v = skip;
                setSkipText();
                break;
            case Bonus.EASY_RESETS:
                v = reset;
                setTimeResetText();
                break;
            case Bonus.EASY_XTRA_TIME:
                v = xtraTime;
                setXtraTimeText();
                break;
        }

        play(bonus.getPlay() == Bonus.INCREASE ? GET_BONUS : USE_BONUS);
        startAnimation(v, 1);

        if (bonus.getPlay() == Bonus.DECREASE) {
            if (bonus.getBonusName().equals(Bonus.EASY_RESETS) || bonus.getBonusName().equals(Bonus.EASY_SKIPS)) {
                runEasy();
            }
        }
    }

    ScoreCallback scoreCallback = new ScoreCallback() {


        @Override
        public void bestScore(Score scr) {
            calc.highScore = scr;
        }
    };


    ScoreListCallback slc = new ScoreListCallback() {
        @Override
        public void scoreSaved(final List <Score> scoreList, String levelName,final long lastScoreId) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setHighScoreTableAdapter(scoreList,lastScoreId);
                }
            });

        }

        @Override
        public void scoreList(List <Score> scoreList) {

        }
    };


}