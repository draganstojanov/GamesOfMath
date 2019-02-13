package com.andraganoid.gameofmath.Fast;

import static com.andraganoid.gameofmath.Game.Game.calc;

public class FastData {

    private String fName;
    private String fDesc;
    private String fScore;
    private StringBuilder sb = new StringBuilder();


    public FastData(String fc, int n, String d, long s) {

        //  fName = n.replace("_"," ");

        sb.setLength(0);
        sb.append(fc);
        sb.append(" ");
        sb.append(n < 10 ? "0" : "");
        sb.append(n);

        fName = sb.toString();
        fDesc = d;
        fScore = "  " + String.valueOf(calc.showTime(s));

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
