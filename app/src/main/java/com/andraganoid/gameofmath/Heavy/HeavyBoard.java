package com.andraganoid.gameofmath.Heavy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.MathBase;
import com.andraganoid.gameofmath.Operation.Hev;
import com.andraganoid.gameofmath.R;


import static com.andraganoid.gameofmath.Operation.Task.eval;

public class HeavyBoard extends Game implements View.OnClickListener {


    TextView qTimer, qResult, qTarget, start, hScore, go, hLives, xtraLives;
    int timerTick;

    String[] signState = new String[4];
    String[] ssl;
    TextView tva[];

    int ch, ch1, ch2;
    TextView hint, xtraTime;
    boolean hintIsNotUsed;
    View board;


    LinearLayout rl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heavy_board);

        //goMain = true;
        isEnd = false;
        calc.highScore = calc.scoreMap.get(calc.levelNames.get(calc.gameKind / 100));


        hScore = findViewById(R.id.heavy_score);
        go = findViewById(R.id.heavy_go);

        board = findViewById(R.id.heavy_board_lay);
        start = findViewById(R.id.heavy_start);
        qTimer = findViewById(R.id.heavy_timer);
        qResult = findViewById(R.id.heavy_result);
        qTarget = findViewById(R.id.heavy_target);
        rl = findViewById(R.id.result_lay);
        hLives = findViewById(R.id.heavy_lives);


        hint = findViewById(R.id.heavy_hint);
        xtraTime = findViewById(R.id.heavy_xtra_time);
        xtraLives = findViewById(R.id.heavy_xtra_lives);


        tva = new TextView[]
                {findViewById(R.id.heavy_sign_0),
                        findViewById(R.id.heavy_sign_1),
                        findViewById(R.id.heavy_sign_2),
                        findViewById(R.id.heavy_sign_3),
                        findViewById(R.id.heavy_img_0),
                        findViewById(R.id.heavy_img_1),
                        findViewById(R.id.heavy_img_2),
                        findViewById(R.id.heavy_img_3)};

        for (TextView v : tva) {
            v.setOnClickListener(this);
        }

        hScore.setText(calc.resetScore());
        prepHeavy();
    }


    void prepHeavy() {
        Toast.makeText(this, "PREPHEAVY", Toast.LENGTH_LONG).show();
        hScore.setText(calc.heavyScore("nextLvl"));
        calc.gameLevel++;
        checkForBonusesHeavy();

        board.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
        start.setText("3");
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

                task = new Hev();
                start.setVisibility(View.GONE);
                goMain = true;
                runHeavy();
            }
        };
        intro.start();

    }

    void runHeavy() {
        Toast.makeText(this, "RUN HEAVY", Toast.LENGTH_LONG).show();
        rl.setVisibility(View.GONE);
        board.setVisibility(View.VISIBLE);

        qResult.setBackgroundColor(0);
        qResult.setText("");

        qTarget.setText(String.valueOf(task.getResult()));


        ((TextView) findViewById(R.id.heavy_op_0)).setText(String.valueOf(task.getOperandValue(0)));
        ((TextView) findViewById(R.id.heavy_op_1)).setText(String.valueOf(task.getOperandValue(1)));
        ((TextView) findViewById(R.id.heavy_op_2)).setText(String.valueOf(task.getOperandValue(2)));
        ((TextView) findViewById(R.id.heavy_op_3)).setText(String.valueOf(task.getOperandValue(3)));
        ((TextView) findViewById(R.id.heavy_op_4)).setText(String.valueOf(task.getOperandValue(4)));
        ((TextView) findViewById(R.id.heavy_op_4)).setText(String.valueOf(task.getOperandValue(4)));


        ssl = new String[]{"", "", "", "", "+", "-", "\u00D7", "\u00F7"};
        for (int i = 0; i < 8; i++) {
            tva[i].setText(ssl[i]);
        }

        hLives.setText(String.valueOf(calc.lives));
//        hint.setText("Hints: " + String.valueOf(calc.heavyHints));
//        xtraTime.setText("XtraTime: " + String.valueOf(calc.heavyXtraTime));

        ch = 0;
        hintIsNotUsed = true;
        timerTick = 0;

        timerStart(calc.secondsForTask * 1000);
    }

    void timerStart(int sec) {
        colorChange = true;
        cdt = new CountDownTimer(sec + 1000, 500) {
            @Override
            public void onTick(long l) {
                // qTimer.setText("Seconds left: " + String.valueOf(l / 1000));
                Log.d( "heavy tick",String.valueOf(l));
                timerTick = (int) l - 1000;

                calc.secondsRemain = (int) l / 1000;

                if ((l / 1000 - 1) < 11) {
                    if (colorChange) {
                        qTimer.setTextColor(getResources().getColor(R.color.checked));
                    } else {
                        qTimer.setTextColor(getResources().getColor(R.color.info));
                    }
                    colorChange = !colorChange;
                }

            //    if (timerTick > 499) {
                    qTimer.setText(timerTick / 60000 + ":"
                            + String.format("%02d", (timerTick / 1000) % 60));


//                } else {
//                    qTimer.setText("END");
//                    lostLife();
//                }


            }


            @Override
            public void onFinish() {
                qTimer.setTextColor(getResources().getColor(R.color.info));
                qTimer.setText("END");
                lostLife();


            }
        };
        cdt.start();
    }

    void lostLife() {
        calc.lives--;
        if (calc.lives > 0) {
          // Toast.makeText(this, "LOST LIFE", Toast.LENGTH_LONG).show();
            prepHeavy();
        } else {
          //  Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();

            if (calc.heavyXtraLives > 0) {
                checkXtraLives();
            } else {
                heavyGameOver();
            }
        }
    }


    void checkXtraLives() {

        new AlertDialog.Builder(this)
                .setTitle("Out of lives")
                .setMessage("You have " + String.valueOf(calc.heavyXtraLives) + " extra lives. Do you want to use it one now in order to continue game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calc.heavyXtraLives = calc.setBonus(calc.HEAVY_XTRA_LIVES, calc.heavyXtraLives - 1);
                        xtraLives.setText("Xtra Lives: " + String.valueOf(calc.heavyXtraLives));
                        startAnimation(xtraLives, 1);
                        calc.lives++;
                        prepHeavy();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                heavyGameOver();
            }
        }).setCancelable(false)
                .create()
                .show();


    }


    void heavyGameOver() {
        isEnd = true;

        findViewById(R.id.heavy_tr).setVisibility(View.GONE);
        go.setVisibility(View.VISIBLE);

        if (calc.currentScore >= calc.highScore || calc.highScore == 0) {

//            MathBase.getInstance().saveHeavyResult(calc.levelNames.get(calc.gameKind / 100), (long) (calc.currentScore));
            MathBase.getInstance().saveHighScore(calc.levelNames.get(calc.gameKind / 100), (long) (calc.currentScore));
            go.setText("NEW HIGH SCORE");
            startAnimation(go, 5);
            goFire();
        } else {
            go.setText("GAME OVER!");
        }


      //  Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();

    }


    public void resultCommit(View v) {
        if (!isEnd) {
            if (cdt != null) {
                cdt.cancel();
            }
            if (isCorrect()) {
//                if (soundIsOn) {
//                    sRight_answer.start();
//                }
               play(RIGHT_ANSWER);
                hScore.setText(calc.heavyScore("submit", (int) ((1 + (int) (calc.gameKind / 100) / 20) * (((float) calc.secondsRemain / (float) calc.secondsForTask * 100) * (100 + (float) calc.gameLevel) / 20))));

              //  Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
                prepHeavy();
            } else {
                hScore.setText(calc.heavyScore("clear"));
                runHeavy();
                // lostLife();
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (!isEnd) {
            hScore.setText(calc.heavyScore("click"));
            int ix = Integer.parseInt(String.valueOf(view.getTag()));
            ch++;

            switch (ch) {

                case 1:
                    ch1 = ix;
                    tva[ch1].setBackgroundColor(getResources().getColor(R.color.checked));
                    break;

                case 2:
                    ch2 = ix;
                    tva[ch1].setBackgroundColor(getResources().getColor(R.color.base));

                    String x = ssl[ch1];
                    ssl[ch1] = ssl[ch2];
                    ssl[ch2] = x;
                    tva[ch1].setText(ssl[ch1]);
                    tva[ch2].setText(ssl[ch2]);

                    int allSigns = 0;
                    for (int i = 0; i < 4; i++) {

                        signState[i] = "";
                        if (ssl[i] != "") {
                            signState[i] = (ssl[i]);
                            allSigns++;
                        }

                        if (allSigns == 4) {
                            rl.setVisibility(View.VISIBLE);
                            qTarget.setClickable(true);

                            if (isCorrect()) {
                                qTarget.setText("Submit");
                                qResult.setText("OK");
                                qResult.setBackgroundColor(getResources().getColor(R.color.checked));
                            } else {
                                qTarget.setText("Clear");
                                qResult.setText(String.valueOf(yourRes()));
                            }

                        } else {
                            rl.setVisibility(View.GONE);
                            qResult.setBackgroundColor(0);
                            qResult.setText("");
                            qTarget.setClickable(false);
                            qTarget.setText(String.valueOf(task.getResult()));
                        }

                    }

                    ch = 0;
                    break;


            }
        }
    }

    int yourRes() {
        String f = "";
        for (int j = 0; j < 5; j++) {

            f += String.valueOf(task.operandValue[j]);
            if (j < 4) {
                f += signState[j];
            }
        }
        return eval(f);
    }

    boolean isCorrect() {


        if (task.getResult() == yourRes()) {
            return true;
        } else {
            return false;

        }
    }

    public void hint(View v) {
        if (!isEnd) {
            if (calc.heavyHints > 0 && hintIsNotUsed) {

                hScore.setText(calc.heavyScore("hint"));

                hintIsNotUsed = false;


                ssl = new String[]{"", "", "", "", "+", "-", "*", "/"};

                for (int i = 0; i < 4; i++) {

                    int o = calc.getOperationType(i);
                    if (o == 2 || o == 3) {

                        ssl[i] = ssl[4 + o];
                        ssl[4 + o] = "";


                        break;
                    }

                }


                for (int i = 0; i < 8; i++) {
                    tva[i].setText(ssl[i]);
                }

                calc.heavyHints = calc.setBonus(calc.HEAVY_HINTS, calc.heavyHints - 1);
                //  qHints--;
                hint.setText("Hints: " + String.valueOf(calc.heavyHints));
//                if (soundIsOn) {
//                    sUseBonus.start();
//                }
                play(USE_BONUS);
                startAnimation(hint, 1);
            }
        }

    }

    public void xtraTime(View v) {
        if (!isEnd) {

            if (calc.heavyXtraTime > 0) {

                hScore.setText(calc.heavyScore("xtra"));

                if (cdt != null) {
                    cdt.cancel();
                }
                timerStart(timerTick + 30000);
                calc.heavyXtraTime = calc.setBonus(calc.HEAVY_XTRA_TIME, calc.heavyXtraTime - 1);
                // qXtraTime--;
                xtraTime.setText("Xtra Time: " + String.valueOf(calc.heavyXtraTime));
//                if (soundIsOn) {
//                    sUseBonus.start();
//                }
                play(USE_BONUS);
                startAnimation(xtraTime, 1);
            }
        }
    }


    private void checkForBonusesHeavy() {

        hint.setText("Hints: " + String.valueOf(calc.heavyHints));
        xtraTime.setText("Xtra Time: " + String.valueOf(calc.heavyXtraTime));
        xtraLives.setText("Xtra Lives: " + String.valueOf(calc.heavyXtraLives));

        if (calc.gameLevel % 12 == 0) {//add HINT
            calc.heavyHints = calc.setBonus(calc.HEAVY_HINTS, calc.heavyHints + 1);
            hint.setText("Hints: " + String.valueOf(calc.heavyHints));
//            if (soundIsOn) {
//                sGetBonus.start();
//            }
            play(GET_BONUS);
            startAnimation(hint, 1);

        }

        if (calc.gameLevel % 20 == 0) {//add XTRA
            calc.heavyXtraTime = calc.setBonus(calc.HEAVY_XTRA_TIME, calc.heavyXtraTime + 1);
            xtraTime.setText("Xtra Time: " + String.valueOf(calc.heavyXtraTime));
//            if (soundIsOn) {
//                sGetBonus.start();
//            }
            play(GET_BONUS);
            startAnimation(xtraTime, 1);

        }


        if (calc.gameLevel % 33 == 0) {//add XTRA
            calc.heavyXtraLives = calc.setBonus(calc.HEAVY_XTRA_LIVES, calc.heavyXtraLives + 1);
            xtraLives.setText("Xtra Lives: " + String.valueOf(calc.heavyXtraLives));
            startAnimation(xtraLives, 1);
            //EEECT
        }

    }


    public void goPause(View v) {
        if (!isEnd) {
            cdt.cancel();
            findViewById(R.id.pause_dialog).setVisibility(View.VISIBLE);
            // showPauseDialog();

        }
    }

    public void goResume(View v) {
        findViewById(R.id.pause_dialog).setVisibility(View.GONE);
        timerStart(calc.secondsRemain * 1000);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (cdt != null) {
            cdt.cancel();
        }
        if (intro != null) {
            intro.cancel();
        }

        if (goMain) {
            boardIntent = new Intent(this, Game.class);

        } else {
            boardIntent = new Intent(this, HeavySettings.class);

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
        Toast.makeText(this, "HEAVY CALC HELP", Toast.LENGTH_LONG).show();
    }

}
