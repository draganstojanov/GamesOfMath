package com.andraganoid.gameofmath.Easy;

import com.andraganoid.gameofmath.Operation.Calc;

public class Easy extends Calc {

    public Easy() {
        gameMode = "Easy Calc";
        gameLevel = 0;
    }

    @Override
    public String easyScore(String sc) {

        switch (sc) {
            case "nextLvl":
                currentScore += 100;
                break;
            case "click":
                currentScore -= 5;
                break;
            case "clear":
                currentScore -= 10;
                break;
            case "skip":
                currentScore -= 33;
                break;
            case "xtra":
                currentScore -= 25;
                break;
            case "reset":
                currentScore -= 50;
                break;
        }

        return String.valueOf(currentScore);
    }

    @Override
    public String easyScore(String sc, int p) {


        switch (sc) {
            case "submit":
                currentScore += p;
                break;
        }

        return String.valueOf(currentScore);
    }
}
