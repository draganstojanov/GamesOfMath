package com.andraganoid.gameofmath.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Div extends Task {


    public Div() {
        super();




      // setResult(getOperandValue(0) / getOperandValue(1));


        setOperandValue(0,(getOperandValue(0) / getOperandValue(1))*getOperandValue(1));

       // setFormula2();
        setFormula(2);

//
//        this.secondOperand = rnd.nextInt(9) + 2;
//
//        this.result = rnd.nextInt(getRangeMax() - 1) + 2;
//        if (getRangeMax() == 100&&result>10) {
//            result = result - (result % 10);
//        }
//        this.firstOperand = result * secondOperand;
//
//
        this.choices = new ArrayList<>();
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
