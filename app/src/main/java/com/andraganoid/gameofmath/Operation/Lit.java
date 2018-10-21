package com.andraganoid.gameofmath.Operation;

import android.util.Log;

import java.util.Collections;

import static com.andraganoid.gameofmath.Game.calc;

public class Lit extends Task {


    public Lit() {

        setLit();
    }


    void setLit() {
        liteArr.clear();
        liteArr.add(getNumber(1000, 0) % 2);
        liteArr.add(2 + getNumber(1000, 0) % 2);
        liteArr.add(getNumber(1000, 0) % 2);

        Collections.shuffle(liteArr);


        calc.setOperationTypeByIndex(0, liteArr.get(0));
        calc.setOperationTypeByIndex(1, liteArr.get(1));
        calc.setOperationTypeByIndex(2, liteArr.get(2));

        liteArr.clear();
        for (int i = 0; i < 6; i++) {
            int m = 10;
            if (i == 5) {
                m = 100;
            }
            liteArr.add(getNumber(m, m / 10));

        }

        for (int i = 0; i < calc.howManyOperands - 1; i++) {
            int m = liteArr.get(i);
            if (calc.getOperationType(i) == 3) {
                m = m * liteArr.get(i + 1);
                liteArr.set(i, m);
            }

            Log.i("math_litOP", String.valueOf(i) + " = " + String.valueOf(m));


            setOperandValue(i, m);
            setOperandValue(i + 1, liteArr.get(i + 1));
        }

        Collections.shuffle(liteArr);
        Collections.shuffle(liteArr);
        Collections.shuffle(liteArr);

        for (int i = 0; i < 6; i++) {
            liteNumbers[i] = liteArr.get(i);
            Log.i("math_lit1", String.valueOf(liteNumbers[i]));
        }

        setFormula(calc.howManyOperands);

        Log.i("math_LITE BEFORE CHECK", String.valueOf(getResult()));
        Log.i("math_LITE BEFORE CHECK", String.valueOf(result));
        if (result < 1 || result > 99) {
            setLit();
            Log.i("math_LITE AFTER CHECK", String.valueOf(result));
        }
    }
}
