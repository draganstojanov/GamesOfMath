package com.andraganoid.gameofmath.HighScores;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Calendar;

@Entity(tableName = "score-table")
public class Score {

    public static final int SCORE_TYPE_POINTS = 1;
    public static final int SCORE_TYPE_TIME = 2;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo
    private long timestamp;

    @ColumnInfo
    private String levelName;

    @ColumnInfo
    private int scorePoints;

    @ColumnInfo
    private long scoreMillis;

    @ColumnInfo
    private String scoreTimeString;

    @ColumnInfo
    private int scoreType;

    public Score() {
        this.scorePoints = 0;
        this.scoreMillis = 0L;
        this.scoreTimeString = setScoreTimeStringFromMillis(scoreMillis);
    }

    @Ignore
    public Score(String levelName, int scorePoints) {
        this.timestamp = System.currentTimeMillis();
        this.levelName = levelName;
        this.scorePoints = scorePoints;
        this.scoreType = SCORE_TYPE_POINTS;
    }

    @Ignore
    public Score(String levelName, long scoreMillis) {
        this.timestamp = System.currentTimeMillis();
        this.levelName = levelName;
        this.scoreMillis = scoreMillis;
        this.scoreTimeString = setScoreTimeStringFromMillis(scoreMillis);
        this.scoreType = SCORE_TYPE_TIME;
    }

    public static String setScoreTimeStringFromMillis(long ms) {
        int secs = (int) (ms / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return (mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", (int) (ms % 1000)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(int scorePoints) {
        this.scorePoints = scorePoints;
    }

    public long getScoreMillis() {
        return scoreMillis;
    }

    public void setScoreMillis(long scoreMillis) {
        this.scoreMillis = scoreMillis;
    }

    public String getScoreTimeString() {
        return scoreTimeString;
    }

    public void setScoreTimeString(String scoreTimeString) {
        this.scoreTimeString = scoreTimeString;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        StringBuilder sb = new StringBuilder();
        sb.append(cal.get(Calendar.DAY_OF_MONTH))
                .append(".")
                .append(cal.get(Calendar.MONTH) + 1)
                .append(".\n")
                .append(cal.get(Calendar.YEAR))
                .append(".");
        return sb.toString();
    }
}
