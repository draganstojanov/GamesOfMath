package com.andraganoid.gameofmath.Game;


import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.R;

import java.util.ArrayList;

import static com.andraganoid.gameofmath.Misc.MathSounds.RIGHT_ANSWER;
import static com.andraganoid.gameofmath.Misc.MathSounds.WRONG_ANSWER;

public abstract class GameBoard extends GamePlay {

    protected View pBoard, fBoard;
    protected TextView formula;
    protected android.support.constraint.ConstraintLayout multic;
    protected LinearLayout keyboard;

    protected TextView typed;
    protected TextView cho0, cho1, cho2, cho3;

    protected int goodAnswers;
    protected int badAnswers;
    public SpannableString progress;
    public ArrayList<Integer> prog = new ArrayList<>();

    protected String typedResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        Toast.makeText(this, "GAME BOARD CREATE", Toast.LENGTH_SHORT).show();
        formula = findViewById(R.id.game_board_formula);
        multic = findViewById(R.id.multi_choice);
        typed = findViewById(R.id.game_board_typed);
        keyboard = findViewById(R.id.keyboard);
        board = findViewById(R.id.game_board_lay);

        cho0 = findViewById(R.id.game_board_choice_0);
        cho1 = findViewById(R.id.game_board_choice_1);
        cho2 = findViewById(R.id.game_board_choice_2);
        cho3 = findViewById(R.id.game_board_choice_3);

        GamePlay.calc.gameLevel = 1;

        progress = new SpannableString("");
        prog.clear();
        goodAnswers = 0;
        badAnswers = 0;
        formula.setClickable(false);
        isEnd = false;
        prepareTask();


    }

    protected abstract void prepareTask();

    protected abstract void showTask();

    protected abstract void next(String c);

    public abstract void goNext(View v);

    public abstract void goBack(View v);

    public abstract void goPause(View v);

    public abstract void goAgain(View v);


    public void multiChoice(View v) {

        if (GamePlay.task.getResult() == (GamePlay.task.choices).get(Integer.parseInt(v.getTag().toString()))) {
            right();

        } else {
            wrong();
        }
    }

    public void keyboard(View v) {
        if (!isEnd) {
            if (typedResult.equals("0")) {
                typedResult = "";
            }
            if (typedResult.length() < 4) {
                typedResult += v.getTag();
                typed.setText(typedResult);
            }
        }
    }

    public void keyboardEnter(View v) {
        if (!isEnd) {
            if (typedResult.length() > 0) {
                if (GamePlay.calc.gameMode == "Practice") {
                    keyboard.setVisibility(View.INVISIBLE);
                }
                if (GamePlay.task.getResult() == Integer.parseInt(typedResult)) {
                    right();

                } else {
                    wrong();
                }
            }
        }
    }

    public void keyboardC(View v) {
        {
            if (!typedResult.isEmpty()) {
                typedResult = typedResult.substring(0, typedResult.length() - 1);
                typed.setText(typedResult);
            }
        }
    }

    public void goHome(View v) {
        boardIntent = new Intent(this, Game.class);
        boardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(boardIntent);
        finish();
    }


    public void right() {

        play(RIGHT_ANSWER);
        goodAnswers++;
        setProgress("Right!", R.color.base, "\u2713");
    }


    public void wrong() {

        play(WRONG_ANSWER);
        badAnswers++;
        setProgress("Wrong!", R.color.checked, "\u2573");
    }


    public void setProgress(String p, int c, String s) {


        prog.add(c);
        progress = new SpannableString(progress + s);
        for (int i = 0; i < progress.length(); i++) {
            progress.setSpan(new ForegroundColorSpan(getResources().getColor(prog.get(i))), i, i + 1, 0);
        }
        next(p);
    }


}
