package com.andraganoid.gameofmath.HighScores;


import java.util.List;

public class Level {

    public static final String FAST_CALC = "Fast Calc";
    public static final String EASY_CALC = "Easy Calc";
    public static final String HEAVY_CALC = "Heavy Calc";

    private String gameName;
    private List <String> levelName;
    private List <String> levelDesc;
    private StringBuilder sb = new StringBuilder();

    public Level(String gn, List <String> ln, List <String> ld) {

        this.gameName = gn;
        this.levelName = ln;
        this.levelDesc = ld;

    }

    public String getLevel(int indeks) {
        return levelName.get(indeks);
    }

    public String getGameName() {
        return gameName;
    }

    public List <String> getLevelName() {
        return levelName;
    }

    public String getScreenLevelName(int indeks) {

        String n = getGameName();

        sb.setLength(0);
        sb.append(n);
        sb.append(" ");

        switch (n) {

            case FAST_CALC:

                sb.append(indeks + 1 < 10 ? "0" : "");
                sb.append(indeks + 1);

                break;

            case EASY_CALC:

                sb.append(indeks + 2);

                break;

            case HEAVY_CALC:

                sb.append(indeks == 0 ? "10" : "100");

                break;

        }

        return sb.toString();


        //  return levelName.get(indeks).replace("_", " ").toUpperCase();
    }

    public List <String> getLevelDesc() {
        return levelDesc;
    }

    public String getLevelDescItem(int indeks) {
        return levelDesc.get(indeks);
    }
}
