package com.andraganoid.gameofmath.Practice;


import android.content.Intent;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Game.GameBoard;
import com.andraganoid.gameofmath.Misc.FullscreenCallback;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.R;

public class PracticeBoard extends GameBoard {

    @Override
    public void prepareTask() {

        goMain = true;
        pauseBtn.setText(getString(R.string.cancel));
        pauseBtn.setClickable(true);
        findViewById((R.id.practice_progress)).setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.GONE);
        switch (calc.getAnswerType()) {
            case "multichoice":
                typed.setVisibility(View.INVISIBLE);
                multic.setVisibility(View.VISIBLE);
                keyboard.setVisibility(View.GONE);
                break;
            case "calculate":
                keyboard.setVisibility(View.VISIBLE);
                typed.setVisibility(View.VISIBLE);
                multic.setVisibility(View.GONE);
                break;
        }
        showTask();
    }

    @Override
    public void showTask() {
        switch (calc.getOperationType(0)) {
            case 0:
                task = new Add();
                break;
            case 1:
                task = new Sub();
                break;
            case 2:
                task = new Mul();
                break;
            case 3:
                task = new Div();
                break;
        }
        typedResult = "";
        formula.setBackgroundColor(getResources().getColor(R.color.base));
        formula.setText(task.getFormula());
        typed.setText(typedResult);
        cho0.setText(String.valueOf(task.choices.get(0)));
        cho1.setText(String.valueOf(task.choices.get(1)));
        cho2.setText(String.valueOf(task.choices.get(2)));
        cho3.setText(String.valueOf(task.choices.get(3)));
    }

    @Override
    public void next(String c) {
        ((TextView) findViewById(R.id.pprog)).setText(progress);
        if (calc.getAnswerType().equals("multichoice")) {
            multic.setVisibility(View.INVISIBLE);
        }
        calc.gameLevel++;
        typed.setVisibility(View.VISIBLE);
        typed.setText(String.valueOf(task.getResult()));
        formula.setText(c);
        nextBtn.setVisibility(View.VISIBLE);
        pauseBtn.setClickable(false);
    }

    @Override
    public void goNext(View v) {
        if (isEnd) {
            showFullscreenAd(fc);
        } else {
            if (calc.gameLevel > calc.getHowManyTasks()) {
                isEnd = true;
                formula.setText(getString(R.string.pright) + String.valueOf(goodAnswers));
                typed.setText(getString(R.string.pwrong) + String.valueOf(badAnswers));
            } else {
                prepareTask();
            }
        }
    }

    FullscreenCallback fc = new FullscreenCallback() {
        @Override
        public void afterFullscreenAd() {
            turnTheScreenOff();
            (findViewById(R.id.again_or_leaderboard)).setVisibility(View.VISIBLE);
            (findViewById(R.id.go_leaderboard)).setVisibility(View.GONE);
        }
    };

    public void goPause(View v) {
        goMain = true;
        finish();
    }

    @Override
    public void goAgain(View v) {
        (findViewById(R.id.again_or_leaderboard)).setVisibility(View.GONE);
        calc.gameLevel = 1;
        progress = new SpannableString("");
        ((TextView) findViewById(R.id.pprog)).setText(progress);
        prog.clear();
        goodAnswers = 0;
        badAnswers = 0;
        isEnd = false;
        prepareTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!adIsShowing) {
            if (goMain) {
                boardIntent = new Intent(this, Game.class);
            } else {
                boardIntent = new Intent(this, PracticeSettings.class);
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
