package com.andraganoid.gameofmath.DataBase;

import java.util.List;

public interface BonusCallback {

    void easy(Bonus bonus);

    void heavy(Bonus bonus);

    void game(List <Bonus> bonusesForGame);
}
