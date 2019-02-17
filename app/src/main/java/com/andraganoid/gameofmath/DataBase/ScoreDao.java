package com.andraganoid.gameofmath.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scorePoints DESC")
    public List <Score> getScoreListPoints(String levelName);

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scorePoints DESC LIMIT 1")
    public Score getBestScorePoints(String levelName);

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scoreMillis ASC")
    public List <Score> getScoreListTime(String levelName);

    @Query("SELECT * FROM `score-table` WHERE levelName= :levelName ORDER BY scoreMillis ASC LIMIT 1")
    public Score getBestScoreTime(String levelName);

   // @Insert
  //  public void saveScore(Score score);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveScore(Score score);


    @Query("SELECT COUNT(*) FROM `score-table` WHERE levelName= :levelName")//mozda i ne treba
    public int countScores(String levelName);//napravi if>50 brisi visak

    // @Query("DELETE FROM `score-table`")
    //  void deleteScorePoints(Score score);

    @Delete
    void deleteScore(Score score);

//    @Delete
//    void deleteScorePoints(Score score);
//
//    @Delete
//    void deleteScoreTime(Score score);

}
