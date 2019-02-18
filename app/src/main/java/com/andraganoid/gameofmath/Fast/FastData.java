package com.andraganoid.gameofmath.Fast;

import com.andraganoid.gameofmath.DataBase.Score;

import static com.andraganoid.gameofmath.Game.Game.calc;

public class FastData {

    private String fName;
    private String fDesc;
    private String fScore;
    //  private StringBuilder sb = new StringBuilder();


//    public FastData(String fc, int n, String d, long s) {
//
//        //  fName = n.replace("_"," ");
//
//        sb.setLength(0);
//        sb.append(fc);
//        sb.append(" ");
//        sb.append(n < 10 ? "0" : "");
//        sb.append(n);
//
//        fName = sb.toString();
//        fDesc = d;
//       // fScore = "  " + String.valueOf(calc.showTime(s));
//        fScore="  " + Score.setScoreTimeStringFromMillis(s);
//
//    }


    public FastData(String fName, String fDesc, String fScore) {
        this.fName = fName;
        this.fDesc = fDesc;
        this.fScore = fScore;

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
