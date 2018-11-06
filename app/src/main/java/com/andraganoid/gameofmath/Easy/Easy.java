package com.andraganoid.gameofmath.Easy;

import com.andraganoid.gameofmath.Calc;
import com.andraganoid.gameofmath.MathBase;

import java.util.List;

//import static com.andraganoid.gameofmath.Game.mathBase;

public class Easy extends Calc {


    public Easy(List<String> ln) {

        gameMode = "Easy Calc";
        levelNames = ln;
        gameLevel = 0;

        scoreMap.clear();

        scoreMap.putAll(MathBase.getInstance().getHighScores(ln));

//        easySkips = MathBase.getInstance().getBonusValue(EASY_SKIPS);
//        if (easySkips == -1) {
//            easySkips = setBonus(EASY_SKIPS, DEFAULT_EASY_SKIPS);
//        }
//
//        easyXtraTime = MathBase.getInstance().getBonusValue(EASY_XTRA_TIME);
//        if (easyXtraTime == -1) {
//            easyXtraTime = setBonus(EASY_XTRA_TIME, DEFAULT_EASY_XTRA_TIME);
//        }
//
//        easyResets = MathBase.getInstance().getBonusValue(EASY_RESETS);
//        if (easyResets == -1) {
//            easyResets = setBonus(EASY_RESETS, DEFAULT_EASY_RESET);
//        }

        easySkips = MathBase.getInstance().getBonusValue(EASY_SKIPS);
        easyXtraTime = MathBase.getInstance().getBonusValue(EASY_XTRA_TIME);
        easyResets = MathBase.getInstance().getBonusValue(EASY_RESETS);

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

// (int)(10*((float)sec/(float)secondsLeft*100)*(100+(float)lLevel)/100))
                currentScore += p;
                break;
        }

        return String.valueOf(currentScore);

    }


}
