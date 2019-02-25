package com.andraganoid.gameofmath.Operation;

import com.andraganoid.gameofmath.DataBase.Bonus;
import com.andraganoid.gameofmath.HighScores.Score;
import com.andraganoid.gameofmath.HighScores.Level;

public class Calc {

    public static String opSign[] = {"+", "-", "\u00D7", "\u00F7", "RND"};
    private int operationType[] = new int[4];
    public int howManyOperands;
    private int operandMaxVal[] = new int[5];
    private int operandMinVal[] = new int[5];
    public int howManyTasks;
    public String gameMode;
    public String answerType;
    public Bonus easySkips;
    public Bonus easyXtraTime;
    public Bonus easyResets;
    public Bonus heavyHints;
    public Bonus heavyXtraTime;
    public Bonus heavyXtraLives;
    public final int DEFAULT_HEAVY_LIVES = 3;
    public int secondsForTask;
    public int gameLevel;
    public int gameKind;
    public Score highScore;
    public int currentScore;
    public int lives;
    public int secondsRemain;
    public Level level;
    public Task fastList[] = new Task[10];

    public void setHowManyOperands(int howManyOperands) {
        this.howManyOperands = howManyOperands;
    }

    public int getHowManyOperands() {
        return howManyOperands;
    }

    public void setOperationType(int ot0) {
        operationType[0] = ot0;
    }

    public void setOperationTypeByIndex(int x, int v) {
        operationType[x] = v;
    }

    public int getOperationType(int x) {
        return operationType[x];
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

    public int getHowManyTasks() {
        return howManyTasks;
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

