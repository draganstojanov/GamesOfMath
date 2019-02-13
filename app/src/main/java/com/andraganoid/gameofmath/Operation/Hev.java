package com.andraganoid.gameofmath.Operation;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import static com.andraganoid.gameofmath.Game.Game.calc;


public class Hev extends Task {
    private ArrayList<Integer> qop = new ArrayList<>();
    private int q, qa, qs, qm, qd;

    public Hev() {
setQuz1();

    }

    private void setQuz1() {
        qop.clear();
        for (int i = 0; i < 4; i++) {
            qop.add(i);
        }
        Collections.shuffle(qop);


        for (int i = 0; i < 4; i++) {

            switch (qop.get(i)) {
                case 0:
                    qa = i;
                    break;
                case 1:
                    qs = i;
                    break;
                case 2:
                    qm = i;
                    break;
                case 3:
                    qd = i;
                    break;

            }
        }
        if (Math.abs(qm - qd) == 1) {
            setQuz1();
        } else {

            for (int i = 0; i < 4; i++) {
                calc.setOperationTypeByIndex(i, qop.get(i));
            }
            setQuz2();
        }
    }

    private void setQuz2() {

//        calc.setOperandMinValByIndex(0, 1);
//        calc.setOperandMaxValByIndex(0,100);
//        setOperandValue(0, getNumber(0));
//
//        q = 100;
//        if (getOperandValue(0) > 1) {
//            q = 10;
//        }
//
//        calc.setOperandMinValByIndex(1, 1);
//        calc.setOperandMaxValByIndex(1, q);
//        setOperandValue(1, getNumber(1));
//
//        if (getOperandValue(0) ==3){
//        setOperandValue(0, (getOperandValue(0) / getOperandValue(1)) * getOperandValue(1));}

        for (int i = 0; i < 5; i++) {

            q = calc.gameKind;
            if (qm+1==i||qd+1==i) {
                q = 10;
            }
            calc.setOperandMinValByIndex(i, 1);
            calc.setOperandMaxValByIndex(i, q);
            setOperandValue(i, getNumber(i));

            Log.i("math_value",String.valueOf(getOperandValue(i)));
        }

      setOperandValue(qd, (getOperandValue(qd) / getOperandValue(qd + 1)) * getOperandValue(qd + 1));

        setFormula(5);

        if(getOperandValue(qd) / getOperandValue(qd + 1)<1||getResult()<0){setQuz2();}


    }

}



