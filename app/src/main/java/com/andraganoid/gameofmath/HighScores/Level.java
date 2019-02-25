package com.andraganoid.gameofmath.HighScores;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {

    public static final String FAST_CALC = "fast_calc";
    public static final String EASY_CALC = "easy_calc";
    public static final String HEAVY_CALC = "heavy_calc";
    private StringBuilder sb = new StringBuilder();
    private List <String> tempList = new ArrayList <>();
    private List <String> tempList2 = new ArrayList <>();
    private String gameName;
    private String screenGameName;
    private List <String> levelName;
    private List <String> screenLevelName;
    private List <String> levelDesc;
    private Map <String, Score> bestResult;

    public Level(String gameName, String screenGameName, List <String> desc) {
        this.gameName = gameName;
        this.screenGameName = screenGameName;
        this.levelDesc = desc;
        this.bestResult = new HashMap <>();
        switch (gameName) {
            case FAST_CALC:
                this.levelName = getFastLevelName();
                this.screenLevelName = getFastScreenLevelName();
                break;
            case EASY_CALC:
                this.levelName = getEasyLevelName();
                this.screenLevelName = getEasyScreenLevelName();
                break;
            case HEAVY_CALC:
                this.levelName = getHeavyLevelName();
                this.screenLevelName = getHeavyScreenLevelName();
                break;
        }
    }

    private List <String> getFastLevelName() {
        tempList.clear();
        for (int i = 1; i <= 14; i++) {
            sb.setLength(0);
            sb.append(FAST_CALC).append("_");
            if (i < 10) {
                sb.append(0);
            }
            sb.append(i);
            tempList.add(sb.toString());
        }
        return tempList;
    }

    private List <String> getFastScreenLevelName() {
        tempList2.clear();
        for (int i = 1; i <= 14; i++) {
            sb.setLength(0);
            sb.append(screenGameName).append(" ");
            if (i < 10) {
                sb.append(0);
            }
            sb.append(i);
            tempList2.add(sb.toString());
        }
        return tempList2;
    }

    private List <String> getEasyLevelName() {
        tempList.clear();
        for (int i = 2; i <= 4; i++) {
            sb.setLength(0);
            sb.append(EASY_CALC).append("_").append(i);
            tempList.add(sb.toString());
            System.out.println("NAME EASY: " + sb);
        }
        return tempList;
    }

    private List <String> getEasyScreenLevelName() {
        tempList2.clear();
        for (int i = 2; i <= 4; i++) {
            sb.setLength(0);
            sb.append(screenGameName).append(" ").append(i);
            tempList2.add(sb.toString());
        }
        return tempList2;
    }

    private List <String> getHeavyLevelName() {
        tempList.clear();
        sb.setLength(0);
        sb.append(HEAVY_CALC).append("_10");
        tempList.add(sb.toString());
        System.out.println("NAME HEAVY: " + sb);
        sb.setLength(0);
        sb.append(HEAVY_CALC).append("_100");
        tempList.add(sb.toString());
        System.out.println("NAME HEAVY: " + sb);
        return tempList;
    }

    private List <String> getHeavyScreenLevelName() {
        tempList2.clear();
        sb.setLength(0);
        sb.append(screenGameName).append(" 10");
        tempList2.add(sb.toString());
        sb.append(0);
        tempList2.add(sb.toString());
        return tempList2;
    }

    public String getGameName() {
        return gameName;
    }

    public String getScreenGameName() {
        return screenGameName;
    }

    public List <String> getLevelName() {
        return levelName;
    }

    public void setLevelName(List <String> levelName) {
        this.levelName = levelName;
    }

    public String getScreenLevelNameItem(int ideks) {
        return screenLevelName.get(ideks);
    }

    public Map <String, Score> getBestResult() {
        return bestResult;
    }

    public String getLevelDescItem(int indeks) {
        return levelDesc.get(indeks);
    }

    public String getLevelNameItem(int indeks) {
        return levelName.get(indeks);
    }

    public String getLevelScreenNameItem(int indeks) {
        return screenLevelName.get(indeks);
    }

    public Score getBestResultItem(String key) {
        return bestResult.get(key);
    }

    public boolean setBestResultItem(String key, Score score) {
        this.bestResult.put(key, score);
        if (bestResult.size() != levelName.size()) {
            return false;
        } else {
            return true;
        }
    }
}