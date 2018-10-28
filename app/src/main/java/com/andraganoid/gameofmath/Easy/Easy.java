package com.andraganoid.gameofmath.Easy;

import android.util.Log;

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
        for (int i = 0; i < levelNames.size(); i++) {

            scoreMap.put(levelNames.get(i), 0L);
            Log.i("score_ehs_scoreMap", String.valueOf(scoreMap));
        }

        scoreMap.putAll(MathBase.getInstance().getEasyHiScores());
        Log.i("score_ehs_scoreMap", String.valueOf(scoreMap));

        easySkips = MathBase.getInstance().getBonusValue(EASY_SKIPS);
        if (easySkips == -1) {
            easySkips = setBonus(EASY_SKIPS, DEFAULT_EASY_SKIPS);
        }

        easyXtraTine = MathBase.getInstance().getBonusValue(EASY_XTRA_TIME);
        if (easyXtraTine == -1) {
            easyXtraTine = setBonus(EASY_XTRA_TIME, DEFAULT_EASY_XTRA_TIME);
        }

        easyResets = MathBase.getInstance().getBonusValue(EASY_RESETS);
        if (easyResets == -1) {
            easyResets = setBonus(EASY_RESETS, DEFAULT_EASY_RESET);
        }

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
