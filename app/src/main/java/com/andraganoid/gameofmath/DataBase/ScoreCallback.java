package com.andraganoid.gameofmath.DataBase;

import java.util.List;

public interface ScoreCallback {
    void scoreSaved(List <Score> scoreList, String levelName);

    void bestScore(Score scr);

    void scoreList(List<Score> scoreListPoints);
}
