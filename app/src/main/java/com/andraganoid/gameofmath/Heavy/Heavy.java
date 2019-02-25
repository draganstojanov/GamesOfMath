package com.andraganoid.gameofmath.Heavy;

import com.andraganoid.gameofmath.Operation.Calc;

public class Heavy extends Calc {

    public Heavy() {
        gameMode = "Heavy Calc";
        lives = DEFAULT_HEAVY_LIVES;
        gameLevel = 0;
    }

    @Override
    public String heavyScore(String sc) {
        switch (sc) {
            case "nextLvl":
                currentScore += 500;
                break;
            case "click":
                currentScore -= 25;
                break;
            case "hint":
                currentScore -= 250;
                break;
            case "xtra":
                currentScore -= 100;
                break;
            case "clear":
                currentScore -= 100;
                break;
        }
        return String.valueOf(currentScore);
    }

    @Override
    public String heavyScore(String sc, int p) {
        switch (sc) {
            case "submit":
                currentScore += p;
                break;
        }
        return String.valueOf(currentScore);
    }
}
