package com.andraganoid.gameofmath.Operation;

import java.util.ArrayList;
import java.util.Collections;

import static com.andraganoid.gameofmath.Game.Game.calc;

public class Hev extends Task {
    private ArrayList <Integer> qop = new ArrayList <>();
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
        for (int i = 0; i < 5; i++) {
            q = calc.gameKind;
            if (qm + 1 == i || qd + 1 == i) {
                q = 10;
            }
            calc.setOperandMinValByIndex(i, 1);
            calc.setOperandMaxValByIndex(i, q);
            setOperandValue(i, getNumber(i));
        }
        setOperandValue(qd, (getOperandValue(qd) / getOperandValue(qd + 1)) * getOperandValue(qd + 1));
        setFormula(5);
        if (getOperandValue(qd) / getOperandValue(qd + 1) < 1 || getResult() < 0) {
            setQuz2();
        }
    }
}



