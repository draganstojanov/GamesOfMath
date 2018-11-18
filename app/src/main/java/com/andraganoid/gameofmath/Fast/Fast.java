package com.andraganoid.gameofmath.Fast;

import com.andraganoid.gameofmath.Operation.Calc;

import java.util.List;

public class Fast extends Calc {

    public Fast(List<String> ln) {
        howManyTasks = 10;
        gameMode = "Fast Calc";
        levelNames=ln;

    }
}
