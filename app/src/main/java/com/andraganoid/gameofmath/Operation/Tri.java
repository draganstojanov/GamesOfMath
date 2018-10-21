package com.andraganoid.gameofmath.Operation;


import static com.andraganoid.gameofmath.Game.calc;

public class Tri extends Task {

    public Tri() {
        setBigS();
    }

    private void setBigS() {
        setOperandValue(0, getNumber(0));
        setOperandValue(1, getNumber(1));
        setOperandValue(2, getNumber(2));

        if (calc.getOperationType(0) == 3) {
            setOperandValue(0, (getOperandValue(0) / getOperandValue(1)) * getOperandValue(1));
        }

        //setFormula3();
        setFormula(3);
        if (getResult() < 0) {
            setBigS();
        }

    }

}
