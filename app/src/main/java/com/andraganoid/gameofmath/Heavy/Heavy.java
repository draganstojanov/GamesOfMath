package com.andraganoid.gameofmath.Heavy;

import com.andraganoid.gameofmath.Operation.Calc;
import com.andraganoid.gameofmath.Misc.MathBase;

import java.util.List;

//import static com.andraganoid.gameofmath.Game.Game.mathBase;


public class Heavy extends Calc {


    public Heavy(List<String> ln) {
        gameMode = "Heavy Calc";
        lives = DEFAULT_HEAVY_LIVES;

        levelNames = ln;
        gameLevel = 0;


        scoreMap.clear();

        scoreMap.putAll(MathBase.getInstance().getHighScores(ln));

//        heavyHints = MathBase.getInstance().getBonusValue(HEAVY_HINTS);
//        if (heavyHints == -1) {
//            heavyHints = setBonus(HEAVY_HINTS, DEFAULT_HEAVY_HINTS);
//        }
//
//        heavyXtraTime = MathBase.getInstance().getBonusValue(HEAVY_XTRA_TIME);
//        if (heavyXtraTime == -1) {
//            heavyXtraTime = setBonus(HEAVY_XTRA_TIME, DEFAULT_HEAVY_XTRA_TIME);
//        }
//
//        heavyXtraLives = MathBase.getInstance().getBonusValue(HEAVY_XTRA_LIVES);
//        if (heavyXtraLives == -1) {
//            heavyXtraLives = setBonus(HEAVY_XTRA_LIVES, DEFAULT_HEAVY_XTRA_LIVES);
//        }

//        heavyHints = MathBase.getInstance().getBonusValue(HEAVY_HINTS);
//        heavyXtraTime = MathBase.getInstance().getBonusValue(HEAVY_XTRA_TIME);
//        heavyXtraLives = MathBase.getInstance().getBonusValue(HEAVY_XTRA_LIVES);

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

// (int)(10*((float)sec/(float)secondsLeft*100)*(100+(float)lLevel)/100))
                currentScore += p;
                break;
        }

        return String.valueOf(currentScore);

    }

}
