package com.andraganoid.gameofmath.Operation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


public class Sub extends Task {

    public Sub() {
        setSub();
    }

    private void setSub() {

        setOperandValue(0, getNumber(0));
        setOperandValue(1, getNumber(1));

//       if (getOperandValue(1) >= getOperandValue(0)) {
//           setOperandValue(1, getNumber(getOperandValue(0), calc.getOperandMinVal(1)));
//       }

      //  setFormula2();
        setFormula(2);

        if (getResult() < 1) {
            setSub();
        } else {
            choices = new ArrayList<>();
            int r = rnd.nextInt(4);
            int c;
            for (int i = 0; i < 4; i++) {
                c = r + result - i;
                Log.i("math", String.valueOf(c));
                if (c < 0) {
                    c = i;
                }
                choices.add(c);
            }
            Collections.shuffle(choices);
        }

    }


}