package com.andraganoid.gameofmath.Practice;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Game.GameBoard;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.R;


public class PracticeBoard extends GameBoard {


    @Override
    public void prepareTask() {
        goMain = true;
        ((TextView) findViewById(R.id.board_pause)).setText(getString(R.string.cancel));
        findViewById((R.id.practice_progress)).setVisibility(View.VISIBLE);
        typed.setClickable(false);
        formula.setClickable(false);

        switch (calc.getAnswerType()) {
            case "multichoice":
                //  ((ConstraintLayout) findViewById(R.id.multi_choice)).setVisibility(View.VISIBLE);
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

//        switch (calc.getAnswerType()) {
//            case "multichoice":
//                multic.setVisibility(View.INVISIBLE);
//                break;
//        }


        calc.gameLevel++;

        typed.setVisibility(View.VISIBLE);
        typed.setText(String.valueOf(task.getResult()));
        typed.setClickable(true);



//
//        if (calc.gameLevel > calc.getHowManyTasks()) {f
//            formula.setText("Finish!");
//        } else {
//            formula.setText("Next");
//        }
        formula.setText(c);
        formula.setClickable(true);

    }

    @Override
    public void goNext(View v) {
//        Log.d("prakt",String.valueOf(isEnd));
//        Log.d("prakt",String.valueOf(calc.gameLevel));
        if (isEnd) {
            showFullscreenAd();
           // goMain = false;
           // finish();
        } else {

            if (calc.gameLevel > calc.getHowManyTasks()) {
                isEnd = true;
                formula.setText(getString(R.string.pright) + String.valueOf(goodAnswers));
                typed.setText(getString(R.string.pwrong) + String.valueOf(badAnswers));


            } else {
                prepareTask();
            }

        }


//        if (calc.gameLevel > calc.getHowManyTasks()) {
//            practiceOver();
//            //  onBack = false;
//            //  finish();
//
//        } else {
//            if (!isEnd) {
//                prepareTask();
//            } else {
//                goMain = false;
//                finish();
//            }
//        }
    }


//    void practiceOver() {
//      //  isEnd = true;
//        formula.setText(getString(R.string.pright) + String.valueOf(goodAnswers));
//        typed.setText(getString(R.string.pwrong) + String.valueOf(badAnswers));
//
//       // showFullscreenAd();
//
//    }


    public void goPause(View v) {
        //  if (!isEnd) {
        goMain = true;
        finish();


        //  }
    }

    @Override
    public void goAgain(View v) {

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
