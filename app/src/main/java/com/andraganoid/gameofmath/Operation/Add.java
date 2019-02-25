package com.andraganoid.gameofmath.Operation;

import java.util.Collections;

public class Add extends Task {

    public Add() {
        super();
        setFormula(2);
        choices.clear();
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
