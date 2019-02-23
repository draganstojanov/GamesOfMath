package com.andraganoid.gameofmath.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.andraganoid.gameofmath.HighScores.Score;


@Database(entities = {
        Bonus.class, Score.class},
        version = 1)

public abstract class RoomBase extends RoomDatabase {

    private static RoomBase INSTANCE;

    public abstract BonusDao bonusDao();
    public abstract ScoreDao scoreDao();


    public static RoomBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RoomBase.class,
                    "GamesOfMathDataBase")
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
