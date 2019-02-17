package com.andraganoid.gameofmath.HighScores;


import com.andraganoid.gameofmath.DataBase.Score;

import java.util.ArrayList;
import java.util.List;

public class Level {

//    public static final String SCREEN_FAST_CALC = "Fast Calc";
//    public static final String SCREEN_EASY_CALC = "Easy Calc";
//    public static final String SCREEN_HEAVY_CALC = "Heavy Calc";

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
    private Score bestResult;


    public Level(String gameName,String screenGameName, List <String> desc) {
        this.gameName = gameName;
        this.screenGameName=screenGameName;
        this.levelDesc = desc;

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
        sb.setLength(0);
        sb.append(HEAVY_CALC).append("_100");
        tempList.add(sb.toString());
        return tempList;
    }

    private List <String> getHeavyScreenLevelName() {
        tempList2.clear();
        sb.setLength(0);
        sb.append(screenGameName).append(" 10");
        tempList2.add(sb.toString());
       // sb.setLength(0);
       // sb.append(screenGameName).append(" 100");
        sb.append(0);
        tempList2.add(sb.toString());
        return tempList2;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getScreenGameName() {
        return screenGameName;
    }

    public void setScreenGameName(String screenGameName) {
        this.screenGameName = screenGameName;
    }

    public List <String> getLevelName() {
        return levelName;
    }

    public void setLevelName(List <String> levelName) {
        this.levelName = levelName;
    }

    public List <String> getScreenLevelName() {
        return screenLevelName;
    }

    public void setScreenLevelName(List <String> screenLevelName) {
        this.screenLevelName = screenLevelName;
    }

    public List <String> getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(List <String> levelDesc) {
        this.levelDesc = levelDesc;
    }

    public String getScreenLevelNameItem(int ideks) {
        return screenLevelName.get(ideks);
    }

        public String getLevelDescItem(int indeks) {
        return levelDesc.get(indeks);
    }

    public String getLevelNameItem(int indeks) {
        return levelName.get(indeks);
    }




    //    public Level(String gn, List <String> ln, List <String> ld) {
//
//        this.gameName = gn;
//        this.levelName = ln;
//        this.levelDesc = ld;
//
//    }
//
//    public String getLevel(int indeks) {
//        return levelName.get(indeks);
//    }
//
//    public String getGameName() {
//        return gameName;
//    }
//
//    public List <String> getLevelName() {
//        return levelName;
//    }
//
//    public String getScreenLevelName(int indeks) {
//
//        String n = getGameName();
//
//        sb.setLength(0);
//        sb.append(n);
//        sb.append(" ");
//
//        switch (n) {
//
//            case FAST_CALC:
//
//                sb.append(indeks + 1 < 10 ? "0" : "");
//                sb.append(indeks + 1);
//
//                break;
//
//            case EASY_CALC:
//
//                sb.append(indeks + 2);
//
//                break;
//
//            case HEAVY_CALC:
//
//                sb.append(indeks == 0 ? "10" : "100");
//
//                break;
//
//        }
//
//        return sb.toString();
//
//
//        //  return levelName.get(indeks).replace("_", " ").toUpperCase();
//    }
//
//    public List <String> getLevelDesc() {
//        return levelDesc;
//    }
//
//    public String getLevelDescItem(int indeks) {
//        return levelDesc.get(indeks);
//    }
}
