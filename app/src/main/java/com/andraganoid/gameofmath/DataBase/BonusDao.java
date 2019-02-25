package com.andraganoid.gameofmath.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
