package com.andraganoid.gameofmath.Heavy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.andraganoid.gameofmath.Operation.Hev;
import com.andraganoid.gameofmath.R;

import java.util.List;

import static com.andraganoid.gameofmath.Misc.Sounds.GET_BONUS;
import static com.andraganoid.gameofmath.Misc.Sounds.LOST_LIFE;
import static com.andraganoid.gameofmath.Misc.Sounds.NO_BONUS;
import static com.andraganoid.gameofmath.Misc.Sounds.RIGHT_ANSWER;
import static com.andraganoid.gameofmath.Misc.Sounds.START;
import static com.andraganoid.gameofmath.Misc.Sounds.TIME_IS_OUT;
import static com.andraganoid.gameofmath.Misc.Sounds.USE_BONUS;
import static com.andraganoid.gameofmath.Operation.Task.eval;

public class HeavyBoard extends GamePlay implements View.OnClickListener {

    private TextView qTimer, qResult, qTarget, start, hScore, go, hLives, hint, xtraTime, xtraLives;
    private int timerTick;
    private String[] signState = new String[4];
    private String[] ssl;
    private TextView tva[];
    private int ch, ch1, ch2;
    private boolean hintIsNotUsed;
    private View board;
    private LinearLayout rl;
    private BonusRepository bonusRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heavy_board);
        bonusRepository = new BonusRepository(getApplicationContext());
   new ScoreRepository(getApplicationContext()).getBestPoints(calc.level.getLevelNameItem(calc.gameKind/100), scoreCallback);
        isEnd = false;
        board = findViewById(R.id.heavy_board_lay);
        board.setClickable(false);
        hScore = findViewById(R.id.heavy_score);
        go = findViewById(R.id.heavy_go);
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
        bonusRepository.getBonusesForGame(Level.HEAVY_CALC, bonusCallback);
        prepHeavy();
    }

    void prepHeavy() {
        hScore.setText(calc.heavyScore("nextLvl"));
        calc.gameLevel++;
        board.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
        start.setText("3");
        goMain = false;
        intro = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                    play(START);
                    start.setText(String.valueOf(l / 1000));
            }
            @Override
            public void onFinish() {
                task = new Hev();
                start.setVisibility(View.GONE);
                goMain = true;
                checkForBonusesHeavy();
                runHeavy();
            }
        };
        intro.start();
    }

    void runHeavy() {
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
        ssl = new String[]{"", "", "", "", "+", "-", "\u00D7", "\u00F7"};
        for (int i = 0; i < 8; i++) {
            tva[i].setText(ssl[i]);
        }
        hLives.setText(String.valueOf(calc.lives));
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
                timerTick = (int) l - 1000;
                calc.secondsRemain = (int) l / 1000;
                if ((l / 1000 - 1) < 11) {
                    if (colorChange) {
                        qTimer.setTextColor(getResources().getColor(R.color.checked));
                        play(TIME_IS_OUT);
                    } else {
                        qTimer.setTextColor(getResources().getColor(R.color.info));
                    }
                    colorChange = !colorChange;
                }
                qTimer.setText(timerTick / 60000 + ":"
                        + String.format("%02d", (timerTick / 1000) % 60));
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
            prepHeavy();
        } else {
            if (calc.heavyXtraLives.getValue() > 0) {
                checkXtraLives();
            } else {
                heavyGameOver();
                play(LOST_LIFE);
            }
        }
    }

    void checkXtraLives() {
        AlertDialog adb = new AlertDialog.Builder(HeavyBoard.this,R.style.MyDialogTheme).create();
        adb.setTitle(getString(R.string.out_of_lives));
        adb.setMessage(getString(R.string.out_of_lives_msg_1)
                + String.valueOf(calc.heavyXtraLives.getValue())
                + getString(R.string.out_of_lives_msg_2));
        adb.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bonusRepository.saveBonus(calc.heavyXtraLives, Bonus.DECREASE, bonusCallback);
            }
        });
        adb.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                heavyGameOver();
            }
        });
        adb.setCancelable(false);
        adb.show();
        int titleId = getResources().getIdentifier("alertTitle", "id", "android");
        if (titleId > 0) {
            TextView title = (TextView) adb.findViewById(titleId);
            if (title != null) {
                title.setTypeface(alertTypeface);
            }
        }
        ((TextView) adb.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
        ((Button) adb.getWindow().findViewById(android.R.id.button1)).setTypeface(alertTypeface);
        ((Button) adb.getWindow().findViewById(android.R.id.button2)).setTypeface(alertTypeface);
    }

    void heavyGameOver() {
        isEnd = true;
        findViewById(R.id.heavy_tr).setVisibility(View.GONE);
        go.setVisibility(View.VISIBLE);
        board.setClickable(true);
        if (calc.currentScore >= calc.highScore.getScorePoints() || calc.highScore.getScorePoints() == 0) {
            go.setText(R.string.new_high);
            startAnimation(go, 5);
            goFire();
        } else {
            go.setText(R.string.game_over);
        }
    }

    public void resultCommit(View v) {
        if (!isEnd) {
            if (cdt != null) {
                cdt.cancel();
            }
            if (isCorrect()) {

                hScore.setText(calc.heavyScore("submit",
                        (int) ((1 + (int) (calc.gameKind / 100) / 20) * (((float) calc.secondsRemain / (float) calc.secondsForTask * 100) * (100 + (float) calc.gameLevel) / 20))));
                prepHeavy();
            } else {
                hScore.setText(calc.heavyScore("clear"));
                runHeavy();
                rl.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        rl.setVisibility(View.GONE);
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
                            qTarget.setClickable(true);
                            if (isCorrect()) {
                                rl.setVisibility(View.GONE);
                                qTarget.setText(getString(R.string.right));
                                play(RIGHT_ANSWER);
                                qResult.setBackgroundColor(getResources().getColor(R.color.checked));
                            } else {
                                rl.setVisibility(View.VISIBLE);
                                qTarget.setText(getString(R.string.clear));
                                qResult.setText(String.valueOf(yourRes()));
                            }
                        } else {
                            rl.setVisibility(View.GONE);
                            qTarget.setClickable(false);
                            qTarget.setText(String.valueOf(task.getResult()));
                        }
                    }
                    ch = 0;
                    break;
            }
        }
    }

    private int yourRes() {
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
        return task.getResult() == yourRes();
    }

    public void hint(View v) {
        if (!isEnd) {
            if (calc.heavyHints.getValue() > 0 && hintIsNotUsed) {
                hScore.setText(calc.heavyScore("hint"));
                hintIsNotUsed = false;
                ssl = new String[]{"", "", "", "", "+", "-", "\u00D7", "\u00F7"};
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
                bonusRepository.saveBonus(calc.heavyHints, Bonus.DECREASE, bonusCallback);
            }
            if (calc.heavyHints.getValue() == 0) {
                play(NO_BONUS);
            }
        }
    }

    public void xtraTime(View v) {
        if (!isEnd) {
            if (calc.heavyXtraTime.getValue() > 0) {
                hScore.setText(calc.heavyScore("xtra"));
                if (cdt != null) {
                    cdt.cancel();
                }
                timerStart(timerTick + 30000);
                bonusRepository.saveBonus(calc.heavyXtraTime, Bonus.DECREASE, bonusCallback);
            }else{play(NO_BONUS);}
        }
    }

    public void xtraLives(View v){play(NO_BONUS);}

    private void checkForBonusesHeavy() {
        setHintText();
        setXtraTimeText();
        setXtraLivesText();
        if (calc.gameLevel % 12 == 0) {
            bonusRepository.saveBonus(calc.heavyHints, Bonus.INCREASE, bonusCallback);
        }
        if (calc.gameLevel % 20 == 0) {
            bonusRepository.saveBonus(calc.heavyXtraTime, Bonus.INCREASE, bonusCallback);
        }
        if (calc.gameLevel % 33 == 0) {
            bonusRepository.saveBonus(calc.heavyXtraLives, Bonus.INCREASE, bonusCallback);
        }
    }

    public void goPause(View v) {
        if (!isEnd) {
            cdt.cancel();
            findViewById(R.id.pause_dialog).setVisibility(View.VISIBLE);
            soundState();
        }
    }

    public void goResume(View v) {
        findViewById(R.id.pause_dialog).setVisibility(View.GONE);
        timerStart(calc.secondsRemain * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!adIsShowing) {
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
    }

    public void goHome(View v) {
        goMain = true;
        finish();
    }

    public void goBack(View v) {
        goMain = false;
        finish();
    }

    public void heavyOver(View v) {
        showFullscreenAd(fc);
        board.setClickable(false);
        if (fireTimer != null) {
            fireTimer.cancel();
        }
    }

    FullscreenCallback fc=new FullscreenCallback() {
        @Override
        public void afterFullscreenAd() {
            turnTheScreenOff();
            (findViewById(R.id.highscore_table)).setVisibility(View.VISIBLE);
            (findViewById(R.id.three_btn)).setVisibility(View.VISIBLE);
            ((TextView) (findViewById(R.id.hs_name))).setText(calc.level.getScreenLevelNameItem(calc.gameKind/100));
            new ScoreRepository(getApplicationContext()).saveScore(new Score(calc.level.getLevelNameItem(calc.gameKind/100), calc.currentScore), slc);
        }
    };

    public void goAgain(View v) {
        adIsShowing = true;
        recreate();
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

    private void setHintText() {
        hint.setText(getString(R.string.hints) + String.valueOf(calc.heavyHints.getValue()));
        if (calc.heavyHints.getValue() == 0) {
            hint.setBackgroundColor(Color.TRANSPARENT);
        } else {
            hint.setBackgroundColor(getResources().getColor(R.color.info));
        }
    }

    private void setXtraTimeText() {
        xtraTime.setText(getString(R.string.xtra_time) + String.valueOf(calc.heavyXtraTime.getValue()));
        if (calc.heavyXtraTime.getValue() == 0) {
            xtraTime.setBackgroundColor(Color.TRANSPARENT);
        } else {
            xtraTime.setBackgroundColor(getResources().getColor(R.color.info));
        }
    }

    private void setXtraLivesText() {
        xtraLives.setText(getString(R.string.xtra_lives) + String.valueOf(calc.heavyXtraLives.getValue()));
        if (calc.heavyXtraLives.getValue() == 0) {
            xtraLives.setBackgroundColor(Color.TRANSPARENT);
        } else {
            xtraLives.setBackgroundColor(getResources().getColor(R.color.info));
        }
    }

    BonusCallback bonusCallback = new BonusCallback() {
        @Override
        public void easy(Bonus bonus) {
        }
        @Override
        public void heavy(final Bonus bonus) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bonusHandler(bonus);
                }
            });
        }

        @Override
        public void game(List <Bonus> bonusesForGame) {
            for (Bonus bonus : bonusesForGame) {
                switch (bonus.getBonusName()) {
                    case Bonus.HEAVY_HINTS:
                        calc.heavyHints = bonus;
                        break;
                    case Bonus.HEAVY_XTRA_TIME:
                        calc.heavyXtraTime = bonus;
                        break;
                    case Bonus.HEAVY_XTRA_LIVES:
                        calc.heavyXtraLives = bonus;
                        break;
                }
            }
        }
    };

    private void bonusHandler(Bonus bonus) {
        if (bonus.getPlay() == Bonus.DECREASE && bonus.getBonusName().equals(Bonus.HEAVY_XTRA_LIVES)) {
            setXtraLivesText();
            calc.lives++;
            prepHeavy();
        } else {
            View v = null;
            switch (bonus.getBonusName()) {
                case Bonus.HEAVY_XTRA_LIVES:
                    v = xtraLives;
                    setXtraLivesText();
                    break;
                case Bonus.HEAVY_HINTS:
                    v = hint;
                    setHintText();
                    break;
                case Bonus.HEAVY_XTRA_TIME:
                    v = xtraTime;
                    setXtraTimeText();
                    break;
            }
            play(bonus.getPlay() == Bonus.INCREASE ? GET_BONUS : USE_BONUS);
            startAnimation(v, 1);
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
        public void scoreSaved(final List <Score> scoreList, String levelName, final long lastScoreId) {
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
