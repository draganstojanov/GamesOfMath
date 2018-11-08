package com.andraganoid.gameofmath.Easy;

import static com.andraganoid.gameofmath.Game.calc;

public class FastData {

    private String fName;
    private String fDesc;
    private String fScore;


    public FastData(String n, String d, long s) {

        fName = n.replace("_"," ");
        fDesc = d;
        fScore = s==0?"None":String.valueOf(calc.showTime(s));

    }

    public FastData() {

    }

    public String getfName() {
        return fName;
    }

    public String getfDesc() {
        return fDesc;
    }

    public String getfScore() {
        return fScore;
    }
}
