package com.andraganoid.gameofmath.Operation;

import android.util.Log;

import java.util.Collections;

import static com.andraganoid.gameofmath.Game.Game.calc;

public class Lit extends Task {


    public Lit() {

        setLit();
    }


    void setLit() {
        easyArr.clear();
        easyArr.add(getNumber(1000, 0) % 2);
        easyArr.add(2 + getNumber(1000, 0) % 2);
        easyArr.add(getNumber(1000, 0) % 2);

        Collections.shuffle(easyArr);


        calc.setOperationTypeByIndex(0, easyArr.get(0));
        calc.setOperationTypeByIndex(1, easyArr.get(1));
        calc.setOperationTypeByIndex(2, easyArr.get(2));

        easyArr.clear();
        for (int i = 0; i < 6; i++) {
            int m = 10;
            if (i == 5) {
                m = 100;
            }
            easyArr.add(getNumber(m, m / 10));

        }

        for (int i = 0; i < calc.howManyOperands - 1; i++) {
            int m = easyArr.get(i);
            if (calc.getOperationType(i) == 3) {
                m = m * easyArr.get(i + 1);
                easyArr.set(i, m);
            }

            Log.i("math_litOP", String.valueOf(i) + " = " + String.valueOf(m));


            setOperandValue(i, m);
            setOperandValue(i + 1, easyArr.get(i + 1));
        }

        Collections.shuffle(easyArr);
        Collections.shuffle(easyArr);
        Collections.shuffle(easyArr);

        for (int i = 0; i < 6; i++) {
            easyNumbers[i] = easyArr.get(i);
            Log.i("math_lit1", String.valueOf(easyNumbers[i]));
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
