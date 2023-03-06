package com.andraganoid.gameofmath.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.andraganoid.gameofmath.HighScores.Score;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scorePoints DESC LIMIT 50")
    public List <Score> getScoreListPoints(String levelName);

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scorePoints DESC LIMIT 1")
    public List <Score> getBestScorePoints(String levelName);

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scoreMillis ASC LIMIT 50")
    public List <Score> getScoreListTime(String levelName);

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scoreMillis ASC LIMIT 1")
    public List <Score> getBestScoreTime(String levelName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveScore(Score score);

    @Query("DELETE FROM `score-table` WHERE scorePoints< :points")
    public void deleteOverPoints(int points);

    @Query("DELETE FROM `score-table` WHERE scoreMillis> :time")
    public void deleteOverTimes(long time);

}
