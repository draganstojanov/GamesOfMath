package com.andraganoid.gameofmath.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.andraganoid.gameofmath.HighScores.Score;

@Database(entities = {
        Bonus.class, Score.class},
        version = 1,
        exportSchema = false)

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
}
