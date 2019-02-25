package com.andraganoid.gameofmath.Operation;

import android.util.Log;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.andraganoid.gameofmath.Game.Game.calc;
import static com.andraganoid.gameofmath.Operation.Calc.opSign;

public class Task {

    public String formula;
    public int operandValue[] = new int[5];
    public int result;
    public ArrayList <Integer> choices = new ArrayList <>();
    HashSet <Integer> hashSet;
    private static DoubleEvaluator de = new DoubleEvaluator();
    private static Double dd;
    public Random rnd;
    public int[] easyNumbers = new int[6];
    public List <Integer> easyArr = new ArrayList <>();

    public Task() {
        this.rnd = new Random();
        setOperandValue(0, getNumber(0));
        setOperandValue(1, getNumber(1));
    }

    public void setResult(String f) {
        result = eval(f);
    }

    public int getResult() {
        return result;
    }

    int getNumber(int g) {
        return rnd.nextInt(1 + calc.getOperandMaxVal(g) - calc.getOperandMinVal(g)) + calc.getOperandMinVal(g);
    }

    int getNumber(int g1, int g2) {
        return rnd.nextInt(g1 - g2) + g2;
    }

    public void setFormula(int x) {
        formula = "";
        for (int i = 0; i < x; i++) {
            if (i < x - 1) {
                formula += String.valueOf(operandValue[i])
                        + " "
                        + opSign[calc.getOperationType(i)]
                        + " ";
            } else {
                formula += String.valueOf(operandValue[i])
                        + " =";
            }
        }
        setResult(getFormulaNoEqual());
    }

    public String getFormula() {
        return formula;
    }

    public String getFormulaNoEqual() {
        return formula.replace(" =", "");
    }

    public void setOperandValue(int x, int v) {
        operandValue[x] = v;
    }

    public int getOperandValue(int x) {
        return operandValue[x];
    }

    public static int eval(String ev) {
        if (ev.equals("") || ev.equals(" ")) {
            return 0;
        } else {
            ev = ev.replace("\u00D7", "*");
            ev = ev.replace("\u00F7", "/");
            dd = de.evaluate(ev);
            return dd.intValue();
        }
    }
}

