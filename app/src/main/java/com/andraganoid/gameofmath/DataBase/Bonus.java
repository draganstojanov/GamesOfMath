package com.andraganoid.gameofmath.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.andraganoid.gameofmath.HighScores.Level;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "bonus-table")
public class Bonus {

    public static final String EASY_SKIPS = "easy_skips";
    public static final String EASY_XTRA_TIME = "easy_xtra_times";
    public static final String EASY_RESETS = "easy_resets";
    public static final String HEAVY_HINTS = "heavy_hints";
    public static final String HEAVY_XTRA_TIME = "heavy_xtra_times";
    public static final String HEAVY_XTRA_LIVES = "heavy_xtra_lives";
    private static final int DEFAULT_EASY_SKIPS = 3;
    private static final int DEFAULT_EASY_XTRA_TIME = 3;
    private static final int DEFAULT_EASY_RESET = 3;
    private static final int DEFAULT_HEAVY_HINTS = 3;
    private static final int DEFAULT_HEAVY_XTRA_TIME = 3;
    private static final int DEFAULT_HEAVY_XTRA_LIVES = 0;
    public static final int INCREASE = 1;
    public static final int DECREASE = -1;


    @PrimaryKey
    @NonNull
    private String bonusName;

    @ColumnInfo
    private int value;

    @ColumnInfo
    private String game;

    private int play;

    public Bonus() {
    }

    public Bonus(String game, String bonusName, int defaultEasySkips) {
        this.game = game;
        this.bonusName = bonusName;
        this.value = defaultEasySkips;
    }

    public static List <Bonus> getInitList() {
        List <Bonus> init = new ArrayList <>();
        init.add(new Bonus(Level.EASY_CALC, EASY_SKIPS, DEFAULT_EASY_SKIPS));
        init.add(new Bonus(Level.EASY_CALC, EASY_XTRA_TIME, DEFAULT_EASY_XTRA_TIME));
        init.add(new Bonus(Level.EASY_CALC, EASY_RESETS, DEFAULT_EASY_RESET));
        init.add(new Bonus(Level.HEAVY_CALC, HEAVY_HINTS, DEFAULT_HEAVY_HINTS));
        init.add(new Bonus(Level.HEAVY_CALC, HEAVY_XTRA_TIME, DEFAULT_HEAVY_XTRA_TIME));
        init.add(new Bonus(Level.HEAVY_CALC, HEAVY_XTRA_LIVES, DEFAULT_HEAVY_XTRA_LIVES));

        return init;
    }


    public String getBonusName() {
        return bonusName;
    }

    public void setBonusName(String bonusName) {
        this.bonusName = bonusName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        this.value = val;
        if (value < 0) {
            value = 0;
        }
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }
}

