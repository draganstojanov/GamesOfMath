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
    public List<Bonus> getBonusesForGame(String game);

    @Query("SELECT * FROM `bonus-table` WHERE bonusName= :bonusName")
    public Bonus getBonus(String bonusName);

   @Insert
    public void initBonuses(List<Bonus> initBonuses);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void saveBonus(Bonus bonus);

    @Query("SELECT COUNT(*) FROM `bonus-table`")
   public int countBonuses();

//
//    @Query("SELECT * FROM `live-table` ORDER BY stream_id ASC")
//    List <Live> getAllLive();
//
//    @Query("SELECT * FROM `live-table` WHERE category_id = :cid ORDER BY stream_id ASC")
//    List <Live> getLiveByCategory(String cid);
//
//    @Insert
//    void liveInsert(Live live);
//
//    @Query("DELETE FROM `live-table`")
//    void deleteLive();
//
//    @Query("SELECT COUNT(*) FROM `live-table`")
//    int getLiveCount();
//
//    @Query("SELECT * FROM `live-table` WHERE stream_type ='radio_streams' ORDER BY stream_id ASC")
//    List <Live> getAllRadio();
//
//    @Query("SELECT COUNT(*) FROM `live-table` WHERE stream_type ='radio_streams' ORDER BY stream_id ASC")
//    int getRadioCount();

}
