package com.andraganoid.gameofmath.DataBase;

import com.andraganoid.gameofmath.HighScores.Score;

import java.util.List;

public interface ScoreListCallback {

    void scoreSaved(List <Score> scoreList, String levelName, long lastScoreId);

    void scoreList(List <Score> scoreList);
}
