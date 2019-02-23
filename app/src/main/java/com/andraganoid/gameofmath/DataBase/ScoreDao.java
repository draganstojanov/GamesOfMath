package com.andraganoid.gameofmath.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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

    @Query("SELECT COUNT(*) FROM `score-table` WHERE levelName= :levelName")//mozda i ne treba
    public int countScores(String levelName);//napravi if>50 brisi visak

    @Delete
    void deleteScore(Score score);

    @Query("DELETE FROM `score-table` WHERE scorePoints< :points")
    public void deleteOverPoints(int points);

    @Query("DELETE FROM `score-table` WHERE scoreMillis> :time")
    public void deleteOverTimes(long time);

}
