package com.andraganoid.gameofmath.Arcade;

import com.andraganoid.gameofmath.Calc;

import java.util.List;

public class Arcade extends Calc {


    public Arcade(List<String> ln) {
        howManyTasks = 10;
        gameMode = "Arcade";
        levelNames=ln;

    }
}
