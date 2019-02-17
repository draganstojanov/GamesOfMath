package com.andraganoid.gameofmath.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import com.andraganoid.gameofmath.HighScores.Level;

import java.util.Arrays;
import java.util.List;

public class BonusRepository {

    private BonusDao bonusDao;
    //  private ScoreDao scoreDao;
    // private Context context;

    public BonusRepository(Context context) {
        RoomBase db = RoomBase.getDatabase(context);
        bonusDao = db.bonusDao();
        //  scoreDao = db.scoreDao();
        //  this.context = context;
    }

    public void initBonuses() {
        new InitBonus(bonusDao).execute(Bonus.getInitList());
    }

    public void getBonusesForGame(String game, BonusCallback bonusCallback) {
        new GetBonusesForGame(bonusDao, bonusCallback).execute(game);
    }

    public void setRewardBonus(String bonusName, int diff) {

        new SetRewardBonus(bonusDao).execute(bonusName,String.valueOf(diff));
    }

    public void saveBonus(Bonus bonus, int diff, BonusCallback bonusCallback) {

        bonus.setValue(bonus.getValue() + diff);
        bonus.setPlay(diff / Math.abs(diff));

        new SaveBonus(bonusDao, bonusCallback).execute(bonus);
    }

    public void getBonus() {
        //new GetBonus(bonusDao).execute();
    }

    private static class InitBonus extends AsyncTask <List <Bonus>, Void, Void> {

        private BonusDao dao;

        InitBonus(BonusDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List <Bonus>... lists) {
//
//            System.out.println("INIT1: "+lists[0].get(0).getValue());
//            System.out.println("INIT1: "+lists[0].get(1).getValue());
//            System.out.println("INIT1: "+lists[0].get(2).getValue());
//            System.out.println("INIT1: "+lists[0].get(3).getValue());
//            System.out.println("INIT1: "+lists[0].get(4).getValue());
//            System.out.println("INIT1: "+lists[0].get(5).getValue());


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
            // System.out.println("SAVE-BONUS: " + bonus[0].getEasy_skips());
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
            Bonus b=dao.getBonus(strings[0]);
            b.setValue(b.getValue() + Integer.parseInt(strings[1]));
            System.out.println("INIT1: "+b.getBonusName());
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

//        @Override
//        protected void onPostExecute(Bonus bonus) {
//            super.onPostExecute(bonus);
//            System.out.println("GET-BONUS: " + bonus.getEasy_skips());
//        }
    }


//    private static class GetBonus extends AsyncTask <Void, Void, Bonus> {
//
//        private BonusDao dao;
//
//         GetBonus(BonusDao dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Bonus doInBackground(Void... voids) {
//
//            System.out.println("GETGET-BONUS: " + dao.getBonus().getEasy_skips());
//            return dao.getBonus();
//
//        }
//
////        @Override
////        protected void onPostExecute(Bonus bonus) {
////            super.onPostExecute(bonus);
////            System.out.println("GET-BONUS: " + bonus.getEasy_skips());
////        }
//    }

}







