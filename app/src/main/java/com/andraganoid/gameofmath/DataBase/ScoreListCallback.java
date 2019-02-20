package com.andraganoid.gameofmath.DataBase;

import java.util.List;

public interface ScoreListCallback {

    void scoreSaved(List <Score> scoreList, String levelName);

    void scoreList(List<Score> scoreList);
}
