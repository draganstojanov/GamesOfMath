package com.andraganoid.gameofmath.Easy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.MathBase;
import com.andraganoid.gameofmath.Operation.Lit;
import com.andraganoid.gameofmath.R;


import java.util.ArrayList;

import static com.andraganoid.gameofmath.Operation.Task.eval;

public class EasyBoard extends Game {

    TextView lNum[], lOper[], lEr[];
    ArrayList<Integer> easyFornulaArr = new ArrayList<>();
    TextView lResult, lTarget, lErase, lTimer, skip, xtraTime, reset, start, eScore, formula;
    boolean isNum;

    int secondsLeft;
    View board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_board);
        board = findViewById(R.id.easy_board_lay);


        calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind));


        //  findViewById(R.id.lite_board_lay).setBackground(new BitmapDrawable(getResources(), background));
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
        intro = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
                if (l < 4000) {
                    start.setText(String.valueOf(l / 1000));
                }
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

    void runEasy() {

        Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
        calc.gameLevel++;
        checkForBonusesEasy();


        formula.setClickable(false);

        //  board.setVisibility(View.VISIBLE);
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


    void timerStart(int s) {
        colorChange = true;
        cdt = new CountDownTimer(s * 1000 + 1000 + 250, 500) {
            @Override
            public void onTick(long l) {
                calc.secondsRemain = (int) l / 1000;
                if (l > 2000) {
                    lTimer.setTextColor(getResources().getColor(R.color.info));
                    lTimer.setText(String.valueOf(l / 1000 - 1));

                    if ((l / 1000 - 1) < 11) {
                        if (colorChange) {
                            lTimer.setTextColor(getResources().getColor(R.color.checked));
                            play(TIME_IS_OUT);
                        }
//
                        colorChange = !colorChange;
                    }


                } else {
                    lTimer.setText("0");
                    easyGameOver();
                }

            }

            @Override
            public void onFinish() {
                lTimer.setTextColor(getResources().getColor(R.color.info));
            }
        };
        cdt.start();
    }

    void easyGameOver() {

        isEnd = true;

        if (calc.currentScore >= calc.highScore || calc.highScore == 0) {

//            MathBase.getInstance().saveEasyResult(calc.levelNames.get(calc.gameKind), (long) (calc.currentScore));
            MathBase.getInstance().saveHighScore(calc.levelNames.get(calc.gameKind), (long) (calc.currentScore));
            formula.setText(getString(R.string.new_high));
            startAnimation(formula, 5);
            goFire();
        } else {
            formula.setText(getString(R.string.game_over));
            play(LOSE_LIFE);
        }
        formula.setClickable(true);

    }

    void writeFormula() {

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
//            if (soundIsOn) {
//                sRight_answer.start();
//            }
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

    boolean isMax() {
        return (easyFornulaArr.size() < (2 * calc.getHowManyOperands() - 1));
    }


    void prepare4click(TextView[] bv) {

        for (TextView tv : bv) {

            tv.setClickable(true);
            tv.setBackground(getResources().getDrawable(R.drawable.tv_stroke));
        }
    }


    void unPrepare4click(TextView[] bv) {

        for (TextView tv : bv) {

            tv.setClickable(false);
            tv.setBackgroundColor(getResources().getColor(R.color.base));
        }


    }


    public void easyReset(View v) {
        if (!isEnd) {

            if (calc.easyResets > 0) {

                eScore.setText(calc.easyScore("reset"));
                if (cdt != null) {
                    cdt.cancel();
                }
                secondsLeft = calc.secondsForTask + 1;
                calc.easyResets = calc.setBonus(calc.EASY_RESETS, calc.easyResets - 1);

                setTimeResetText();
                play(USE_BONUS);
                startAnimation(reset, 1);
                runEasy();

            }
        }
    }


    public void easySkip(View v) {
        if (!isEnd) {

            if (calc.easySkips > 0) {
                eScore.setText(calc.easyScore("skip"));
                if (cdt != null) {
                    cdt.cancel();
                }

                calc.easySkips = calc.setBonus(calc.EASY_SKIPS, calc.easySkips - 1);

                setSkipText();
                play(USE_BONUS);
                startAnimation(skip, 1);
                runEasy();
            }


        }
    }

    public void easyXtraTime(View v) {
        if (!isEnd) {
            if (calc.easyXtraTime > 0) {
                eScore.setText(calc.easyScore("xtra"));
                if (cdt != null) {
                    cdt.cancel();
                }

                timerStart(calc.secondsRemain + 30);
                calc.easyXtraTime = calc.setBonus(calc.EASY_XTRA_TIME, calc.easyXtraTime - 1);

                setXtraTimeText();
                play(USE_BONUS);
                startAnimation(xtraTime, 1);
            }
        }
    }


    private void checkForBonusesEasy() {

        setSkipText();
        setXtraTimeText();
        setTimeResetText();

        if (calc.gameLevel % 20 == 0) {//add SKIP
            calc.easySkips = calc.setBonus(calc.EASY_SKIPS, calc.easySkips + 1);
            setSkipText();
            play(GET_BONUS);
            startAnimation(skip, 1);

        }
        if (calc.gameLevel % 24 == 0) {//add XTRA
            calc.easyXtraTime = calc.setBonus(calc.EASY_XTRA_TIME, calc.easyXtraTime + 1);
            setXtraTimeText();
            startAnimation(xtraTime, 1);

        }
        if (calc.gameLevel % 33 == 0) {//add RESET
            calc.easyResets = calc.setBonus(calc.EASY_RESETS, calc.easyResets + 1);
            setTimeResetText();
            play(GET_BONUS);
            startAnimation(reset, 1);
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

        showFullscreenAd();
        //   goMain = false;
        //  finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain = false;
    }


    private void setSkipText() {
        skip.setText(getString(R.string.skips) + String.valueOf(calc.easySkips));
    }

    private void setXtraTimeText() {
        xtraTime.setText(getString(R.string.xtra_time) + String.valueOf(calc.easyXtraTime));
    }

    private void setTimeResetText() {
        reset.setText(getString(R.string.resets) + String.valueOf(calc.easyResets));
    }

}