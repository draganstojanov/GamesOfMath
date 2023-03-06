package com.andraganoid.gameofmath.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BonusDao {

    @Query("SELECT * FROM `bonus-table` WHERE game= :game")
    public List <Bonus> getBonusesForGame(String game);

    @Query("SELECT * FROM `bonus-table` WHERE bonusName= :bonusName")
    public Bonus getBonus(String bonusName);

    @Insert
    public void initBonuses(List <Bonus> initBonuses);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void saveBonus(Bonus bonus);

    @Query("SELECT COUNT(*) FROM `bonus-table`")
    public int countBonuses();

}
