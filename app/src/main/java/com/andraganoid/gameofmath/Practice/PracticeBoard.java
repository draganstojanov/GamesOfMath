package com.andraganoid.gameofmath.Practice;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game;
import com.andraganoid.gameofmath.GameBoard;
import com.andraganoid.gameofmath.Operation.Add;
import com.andraganoid.gameofmath.Operation.Div;
import com.andraganoid.gameofmath.Operation.Mul;
import com.andraganoid.gameofmath.Operation.Sub;
import com.andraganoid.gameofmath.R;

import static com.andraganoid.gameofmath.Game.calc;
import static com.andraganoid.gameofmath.Game.task;


public class PracticeBoard extends GameBoard {


    @Override
    public void prepareTask() {
        goMain = true;
        ((TextView) findViewById(R.id.board_pause)).setText("CANCEL");
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
        //  taskCount.setText(String.valueOf(calc.gameLevel) + "/" + String.valueOf(calc.getHowManyTasks()));

        //   goodAnswer.setText(String.valueOf(goodAnswers));
        //   badAnswer.setText(String.valueOf(badAnswers));
        //  perc.setText(String.valueOf(goodAnswers + badAnswers > 0 ? (int) ((goodAnswers * 100) / (goodAnswers + badAnswers)) : 0) + "%");

        typed.setText(typedResult);
        cho0.setText(String.valueOf(task.choices.get(0)));
        cho1.setText(String.valueOf(task.choices.get(1)));
        cho2.setText(String.valueOf(task.choices.get(2)));
        cho3.setText(String.valueOf(task.choices.get(3)));

    }


    @Override
    public void next(String c) {

        ((TextView) findViewById(R.id.pprog)).setText(progress);

        switch (calc.getAnswerType()) {
            case "multichoice":
                multic.setVisibility(View.INVISIBLE);
                break;
        }

        //  multic.setVisibility(View.INVISIBLE);
        typed.setVisibility(View.VISIBLE);
        typed.setText(String.valueOf(task.getResult()));
        typed.setClickable(true);


        calc.gameLevel++;

        if (calc.gameLevel > calc.getHowManyTasks()) {
            formula.setText("Finish!");
        } else {
            formula.setText("Next");
        }
        //    goodAnswer.setText(String.valueOf(goodAnswers));
        //   badAnswer.setText(String.valueOf(badAnswers));
        //  perc.setText(String.valueOf(goodAnswers + badAnswers > 0 ? (int) ((goodAnswers * 100) / (goodAnswers + badAnswers)) : 0) + "%");
        // formula.setBackgroundColor(c);
        formula.setText(c);
        formula.setClickable(true);

    }

    public void goNext(View v) {

        if (isEnd) {
            goMain = false;
            finish();
        } else {

            if (calc.gameLevel > calc.getHowManyTasks()) {
                practiceOver();
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


    void practiceOver() {
        isEnd = true;
        formula.setText("Right: " + String.valueOf(goodAnswers));
        typed.setText("Wrong: " + String.valueOf(badAnswers));

    }


    public void goPause(View v) {
        //  if (!isEnd) {
        goMain = true;
        finish();


        //  }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (goMain) {
            boardIntent = new Intent(this, Game.class);

        } else {
            boardIntent = new Intent(this, PracticeSettings.class);

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
        Toast.makeText(this, "PRACTICE HELP", Toast.LENGTH_LONG).show();
    }

}
