package com.andraganoid.gameofmath;

import java.util.ArrayList;
import java.util.List;

public class Level {


    private String gameName;
    private List<String> levelName;
    private List <String> levelDesc;

    public Level(String gn,List <String> ln, List <String> ld) {

        this.gameName = gn;
        this.levelName = ln;
        this.levelDesc = ld;

    }

    public String getLevel(int indeks) { return levelName.get(indeks); }

    public String getGameName() {
        return gameName;
    }

    public List <String> getLevelName() {
        return levelName;
    }

    public String getLevelNameItem(int indeks) {
        return levelName.get(indeks).replace("_"," ").toUpperCase();
    }

    public List <String> getLevelDesc() {
        return levelDesc;
    }

    public String getLevelDescItem(int indeks) {
        return levelDesc.get(indeks);
    }
}
