package com.andraganoid.gameofmath.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import com.andraganoid.gameofmath.HighScores.Level;

import java.util.List;

public class BonusRepository {
    private BonusDao bonusDao;

    public BonusRepository(Context context) {
        RoomBase db = RoomBase.getDatabase(context);
        bonusDao = db.bonusDao();
    }

    public void initBonuses() {
        new InitBonus(bonusDao).execute(Bonus.getInitList());
    }

    public void getBonusesForGame(String game, BonusCallback bonusCallback) {
        new GetBonusesForGame(bonusDao, bonusCallback).execute(game);
    }

    public void setRewardBonus(String bonusName, int diff) {

        new SetRewardBonus(bonusDao).execute(bonusName, String.valueOf(diff));
    }

    public void saveBonus(Bonus bonus, int diff, BonusCallback bonusCallback) {

        bonus.setValue(bonus.getValue() + diff);
        bonus.setPlay(diff / Math.abs(diff));
        new SaveBonus(bonusDao, bonusCallback).execute(bonus);
    }

    private static class InitBonus extends AsyncTask <List <Bonus>, Void, Void> {

        private BonusDao dao;

        InitBonus(BonusDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List <Bonus>... lists) {
            if (dao.countBonuses() == 0) {
                dao.initBonuses(lists[0]);
            }
            return null;
        }
    }


    private static class SaveBonus extends AsyncTask <Bonus, Void, Void> {

        private BonusDao dao;
        private BonusCallback bonusCallback;

        SaveBonus(BonusDao dao, BonusCallback bonusCallback) {
            this.dao = dao;
            this.bonusCallback = bonusCallback;
        }

        @Override
        protected Void doInBackground(Bonus... bonus) {
            dao.saveBonus(bonus[0]);
            if (bonusCallback != null) {
                switch (bonus[0].getGame()) {
                    case Level.EASY_CALC:
                        bonusCallback.easy(bonus[0]);
                        break;
                    case Level.HEAVY_CALC:
                        bonusCallback.heavy(bonus[0]);
                        break;
                }
            }
            return null;
        }
    }

    private static class SetRewardBonus extends AsyncTask <String, Void, Void> {

        private BonusDao dao;

        SetRewardBonus(BonusDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            Bonus b = dao.getBonus(strings[0]);
            b.setValue(b.getValue() + Integer.parseInt(strings[1]));
            dao.saveBonus(b);
            return null;
        }
    }


    private static class GetBonusesForGame extends AsyncTask <String, Void, List <Bonus>> {

        private BonusDao dao;
        private BonusCallback bonusCallback;

        GetBonusesForGame(BonusDao dao, BonusCallback bonusCallback) {
            this.dao = dao;
            this.bonusCallback = bonusCallback;
        }

        @Override
        protected List <Bonus> doInBackground(String... strings) {
            dao.getBonusesForGame(strings[0]);
            bonusCallback.game(dao.getBonusesForGame(strings[0]));
            return null;
        }
    }
}







