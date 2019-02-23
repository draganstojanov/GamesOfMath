package com.andraganoid.gameofmath.DataBase;

import com.andraganoid.gameofmath.HighScores.Score;

public interface ScoreCallback {

   // void scoreSaved(List <Score> scoreList, String levelName);

    void bestScore(Score scr);

}
