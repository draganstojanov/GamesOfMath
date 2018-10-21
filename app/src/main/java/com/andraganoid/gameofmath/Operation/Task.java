package com.andraganoid.gameofmath.Operation;

import android.util.Log;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.andraganoid.gameofmath.Game.calc;
import static com.andraganoid.gameofmath.Calc.opSign;

public class Task {


    String formula;

    public int operandValue[] = new int[5];
    int result;
    public ArrayList<Integer> choices = new ArrayList<>();
    HashSet<Integer> hashSet;

    static DoubleEvaluator de = new DoubleEvaluator();
    static Double dd;

    Random rnd;

    public int[] liteNumbers = new int[6];
    public List<Integer> liteArr = new ArrayList<>();

    public Task() {
        this.rnd = new Random();
        setOperandValue(0, getNumber(0));
        setOperandValue(1, getNumber(1));

    }

    //  public void setResult(int result) {
    //     this.result = result;
    //  }
    public void setResult(String f) {
//        f= f.replace("\u00D7","*");
//        f= f.replace("\u00F7","/");
//        Double d = new DoubleEvaluator().evaluate(f);
//        result = d.intValue();
        result = eval(f);
    }

    public int getResult() {
        return result;
    }

    public List<Integer> getChoices() {
        return choices;
    }


    int getNumber(int g) {
        Log.i("math_get", String.valueOf(g));
        // Log.i("math_g+", String.valueOf(calc.getOperandMaxVal(g)));
        // Log.i("math_g-", String.valueOf(calc.getOperandMinVal(g)));


        return rnd.nextInt(1 + calc.getOperandMaxVal(g) - calc.getOperandMinVal(g)) + calc.getOperandMinVal(g);
    }

    int getNumber(int g1, int g2) {

//        Log.i("math_g1", String.valueOf(g1));
//        Log.i("math_g2", String.valueOf(g2));
//        int t = rnd.nextInt(g1 - g2) + g2;
//        Log.i("math_t", String.valueOf(t));
//        return t;
        return rnd.nextInt(g1 - g2) + g2;
    }

    public void setFormula(int x) {
        formula = "";
        for (int i = 0; i < x; i++) {

            if (i < x - 1) {

                Log.i("math_OPTYPE", String.valueOf(calc.getOperationType(i)));
                formula += String.valueOf(operandValue[i])
                        + " "
                        + opSign[calc.getOperationType(i)]
                        + " ";
            } else {
                formula += String.valueOf(operandValue[i])
                        + " =";
            }


        }
        Log.i("math_formula", formula);
        setResult(getFormulaNoEqual());

    }

//    public void setFormula2() {
//        formula = String.valueOf(operandValue[0]) + " "
//                + opSign[calc.getOperationType(0)] + " "
//                + String.valueOf(operandValue[1]) + " =";
//        setResult(getFormulaNoEqual());
//    }

//    public void setFormula3() {
//        formula = String.valueOf(operandValue[0]) + " "
//                + opSign[calc.getOperationType(0)] + " "
//                + String.valueOf(operandValue[1]) + " "
//                + opSign[calc.getOperationType(1)] + " "
//                + String.valueOf(operandValue[2]) + " =";
//        setResult(getFormulaNoEqual());
//    }

//    public void setFormula5() {
//        formula = String.valueOf(operandValue[0]) + " "
//                + opSign[calc.getOperationType(0)] + " "
//                + String.valueOf(operandValue[1]) + " "
//                + opSign[calc.getOperationType(1)] + " "
//                + String.valueOf(operandValue[2]) + " "
//                + opSign[calc.getOperationType(2)] + " "
//                + String.valueOf(operandValue[3]) + " "
//                + opSign[calc.getOperationType(3)] + " "
//                + String.valueOf(operandValue[4]) + " =";
//        setResult(getFormulaNoEqual());
//    }


    public String getFormula() {
        return formula;
    }

    public String getFormulaNoEqual() {
        return formula.replace(" =", "");
    }


    public void setOperandValue(int[] operandValue) {
        this.operandValue = operandValue;
    }

    public void setOperandValue(int x, int v) {
        operandValue[x] = v;
    }

    public int[] getOperandValue() {
        return operandValue;
    }

    public int getOperandValue(int x) {
        return operandValue[x];
    }

    public static int eval(String ev) {

        if (ev == "" || ev == " ") {
            return 0;
        } else {
            ev = ev.replace("\u00D7", "*");
            ev = ev.replace("\u00F7", "/");
            dd = de.evaluate(ev);
            return dd.intValue();
        }
    }

}

