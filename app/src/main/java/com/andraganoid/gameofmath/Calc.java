package com.andraganoid.gameofmath;

import com.andraganoid.gameofmath.Operation.Task;

import java.util.HashMap;
import java.util.List;

import static com.andraganoid.gameofmath.Game.mathBase;

public class Calc {


    //  public static String opName[] = {"Addition", "Substraction", "Multiplication", "Division"};
    public static String opSign[] = {"+", "-", "\u00D7", "\u00F7", "RND"};

    private int operationType[] = new int[4];
    public int howManyOperands;
    private int operandMaxVal[] = new int[5];
    private int operandMinVal[] = new int[5];


    public int howManyTasks;
    public String gameMode;
    public String answerType;

    public int easySkips;
    public int easyXtraTine;
    public int easyResets;
    public int heavyHints;
    public int heavyXtraTime;
    public int heavyXtraLives;

    public final int DEFAULT_EASY_SKIPS = 3;
    public final int DEFAULT_EASY_XTRA_TIME = 3;
    public final int DEFAULT_EASY_RESET = 3;
    public final int DEFAULT_HEAVY_HINTS = 3;
    public final int DEFAULT_HEAVY_XTRA_TIME = 3;
    public final int DEFAULT_HEAVY_XTRA_LIVES = 0;
    public final int DEFAULT_HEAVY_LIVES = 3;

    public final String EASY_SKIPS = "easy_skips";
    public final String EASY_XTRA_TIME = "easy_xtra_times";
    public final String EASY_RESETS = "easy_resets";
    public final String HEAVY_HINTS = "heavy_hints";
    public final String HEAVY_XTRA_TIME = "heavy_xtra_times";
    public final String HEAVY_XTRA_LIVES = "heavy_xtra_lives";


    public HashMap<String, Long> scoreMap = new HashMap<String, Long>();
    public List<String> levelNames;

    public int secondsForTask;
    public int gameLevel;
    public int gameKind;
    public long highScore;
    public int currentScore;
    public int lives;
    public int secondsRemain;


    public Task arcadeList[] = new Task[10];


    public void setOperationType(int[] operationType) {
        this.operationType = operationType;
    }

    public void setHowManyOperands(int howManyOperands) {
        this.howManyOperands = howManyOperands;
    }

    public int getHowManyOperands() {
        return howManyOperands;
    }

    public void setOperationType(int ot0) {
        operationType[0] = ot0;
    }

    public void setOperationType(int ot0, int ot1) {
        operationType[0] = ot0;
        operationType[1] = ot1;
    }

    public void setOperationTypeByIndex(int x, int v) {
        operationType[x] = v;

    }


    public int getOperationType(int x) {
        return operationType[x];
    }

    public void setOperandMaxVal(int[] operandMaxVal) {
        this.operandMaxVal = operandMaxVal;
    }

    public void setOperandMaxValByIndex(int x, int v) {
        operandMaxVal[x] = v;
    }

    public void setOperandMaxVal(int max0, int max1) {
        operandMaxVal[0] = max0;
        operandMaxVal[1] = max1;
    }

    public void setOperandMaxVal(int max0, int max1, int max2) {
        operandMaxVal[0] = max0;
        operandMaxVal[1] = max1;
        operandMaxVal[2] = max2;
    }

    public int getOperandMaxVal(int v) {
        return operandMaxVal[v];
    }


    public void setOperandMinValByIndex(int x, int v) {
        operandMinVal[x] = v;
    }

    public void setOperandMinVal(int min0, int min1) {
        operandMinVal[0] = min0;
        operandMinVal[1] = min1;
    }

    public void setOperandMinVal(int min0, int min1, int min2) {
        operandMinVal[0] = min0;
        operandMinVal[1] = min1;
        operandMinVal[2] = min2;
    }

    public int getOperandMinVal(int v) {
        return operandMinVal[v];
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setHowManyTasks(int howManyTasks) {
        this.howManyTasks = howManyTasks;
    }

    public int getHowManyTasks() {
        return howManyTasks;
    }

    public void setTaskList(int x, Task t) {
    }

    public void setAllGenerated() {
    }

    //  public ArrayList getAllGenerated() {
    //   return allGenerated;
    // }


    public String showTime(Long ms) {
        int secs = (int) (ms / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return ("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", (int) (ms % 1000)));

    }


    public int setBonus(String t, int s) {
        int ss = s;
        if (ss < 0) {
            ss = 0;
        }

        mathBase.saveBonus(t, ss);

        switch (t) {
            case EASY_SKIPS:
                easySkips = ss;
                break;
            case EASY_XTRA_TIME:
                easyXtraTine = ss;
                break;
            case EASY_RESETS:
                easyResets = ss;
                break;
            case HEAVY_HINTS:
                heavyHints = ss;
                break;
            case HEAVY_XTRA_TIME:
                heavyXtraTime = ss;
                break;
            case HEAVY_XTRA_LIVES:
                heavyXtraLives = ss;
                break;
        }

        return ss;
    }

    public String resetScore() {
        currentScore = 0;
        return "0";
    }


    public String easyScore(String sc) {
        return "";
    }

    public String easyScore(String sc, int p) {
        return "";
    }

    public String heavyScore(String sc) {
        return "";
    }

    public String heavyScore(String sc, int p) {
        return "";
    }


}

