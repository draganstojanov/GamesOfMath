package com.andraganoid.gameofmath.DataBase;

import java.util.List;

public interface ScoreCallback {
    void scoreSaved(List <Score> scoreList, String levelName);

    void bestPoints(Score bestScorePoints);

    void bestPointsList(List <Score> scoreListPoints);

    void bestTimes(Score bestScoreTime);

    void bestTimesList(List <Score> scoreListTime);
}
