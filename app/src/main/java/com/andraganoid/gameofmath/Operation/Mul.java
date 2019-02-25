package com.andraganoid.gameofmath.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Mul extends Task {
    public Mul() {
        super();
        setFormula(2);
        this.choices = new ArrayList <>();
        int c;
        for (int i = 0; i < 4; i++) {
            c = (getOperandValue(0) - i) * getOperandValue(1);
            if (c > 0) {
                choices.add(c);
            }
            c = (getOperandValue(0) + i) * getOperandValue(1);
            if (c > 0) {
                choices.add(c);
            }
            c = (getOperandValue(1) - i) * getOperandValue(0);
            if (c > 0) {
                choices.add(c);
            }
            c = (getOperandValue(1) + i) * getOperandValue(1);
            if (c > 0) {
                choices.add(c);
            }
        }
        Collections.shuffle(choices);
        hashSet = new HashSet <>();
        hashSet.addAll(choices);
        choices.clear();
        choices.addAll(hashSet);
        hashSet.clear();
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i) != result && hashSet.size() < 3) {
                hashSet.add(choices.get(i));
            }
        }
        choices.clear();
        choices.addAll(hashSet);
        choices.add(result);
        Collections.shuffle(choices);
    }
}


