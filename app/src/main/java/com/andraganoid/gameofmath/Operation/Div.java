package com.andraganoid.gameofmath.Operation;

import java.util.ArrayList;
import java.util.Collections;

public class Div extends Task {
    public Div() {
        super();
        setOperandValue(0, (getOperandValue(0) / getOperandValue(1)) * getOperandValue(1));
        setFormula(2);
        this.choices = new ArrayList <>();
        int r = rnd.nextInt(4);
        int c;
        for (int i = 0; i < 4; i++) {
            c = r + result - i;
            if (c <= 0) {
                c = i + 1;
            }
            choices.add(c);
        }
        Collections.shuffle(choices);
    }
}
