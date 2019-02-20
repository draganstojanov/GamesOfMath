package com.andraganoid.gameofmath.DataBase;

import android.content.Context;
import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.List;

public class ScoreRepository {

    private static final int MAX_SCORES_IN_LIST = 50;

    private static final int BEST_POINTS = 1;
    private static final int BEST_POINTS_LIST = 2;
    private static final int BEST_TIME = 3;
    private static final int BEST_TIMES_LIST = 4;

    private ScoreDao scoreDao;

    public ScoreRepository(Context context) {
        RoomBase db = RoomBase.getDatabase(context);
        scoreDao = db.scoreDao();
    }

    public void saveScore(Score score, ScoreListCallback scoreCallback) {
        System.out.println("SAVE: "+score.getScoreTimeString()+score.getLevelName()+score.getScorePoints());
        new SaveScore(scoreDao, scoreCallback).execute(score);
    }

    public void getBestPoints(String levelName, ScoreCallback scoreCallback) {
        new GetScore(scoreDao, scoreCallback, BEST_POINTS).execute(levelName);
    }

    public void getBestPointsList(String levelName, ScoreListCallback scoreCallback) {
        new GetScoreList(scoreDao, scoreCallback, BEST_POINTS_LIST).execute(levelName);
    }

//    public void getBestPointsForAll(List<String> levelList, ScoreForAllCallback scoreCallback) {
//        new GetScore(scoreDao, scoreCallback, BEST_POINTS).execute(levelList);
    //   }

    public void getBestTime(String levelName, ScoreCallback scoreCallback) {
        new GetScore(scoreDao, scoreCallback, BEST_TIME).execute(levelName);
    }

    public void getBestTimesList(String levelName, ScoreListCallback scoreCallback) {
        new GetScoreList(scoreDao, scoreCallback, BEST_TIMES_LIST).execute(levelName);
    }

//    public void getBestTimesForAll(List<String> levelList, ScoreForAllCallback scoreCallback) {
//        new GetScore(scoreDao, scoreCallback, BEST_TIME).execute(levelList);
//    }


    private static class SaveScore extends AsyncTask <Score, Void, Void> {

        private ScoreDao dao;
        private ScoreListCallback scoreCallback;

        SaveScore(ScoreDao dao, ScoreListCallback scoreCallback) {
            this.dao = dao;
            this.scoreCallback = scoreCallback;
        }

        @Override
        protected Void doInBackground(Score... scores) {

            Score scr = scores[0];
            List <Score> scoreList = new ArrayList <>();

            dao.saveScore(scr);

            switch (scr.getScoreType()) {

                case Score.SCORE_TYPE_POINTS:
                    scoreList = dao.getScoreListPoints(scr.getLevelName());
                 //   dao.deleteOverPoints(scoreList.get(MAX_SCORES_IN_LIST - 1).getScorePoints());
                    break;

                case Score.SCORE_TYPE_TIME:
                    scoreList = dao.getScoreListTime(scr.getLevelName());
                //    dao.deleteOverTimes(scoreList.get(MAX_SCORES_IN_LIST - 1).getScoreMillis());
                    break;
            }
            //  if (scoreList.size() > MAX_SCORES_IN_LIST) {//xxxxxxxxxxxxxxxxx

//                dao.deleteScore(scoreList.get(MAX_SCORES_IN_LIST));
//                scoreList.remove(MAX_SCORES_IN_LIST);

            // }


            scoreCallback.scoreSaved(scoreList, scr.getLevelName());
            return null;
        }
    }


    private static class GetScoreList extends AsyncTask <String, Void, Score> {

        private ScoreDao dao;
        private ScoreListCallback scoreCallback;
        private int getType;

        GetScoreList(ScoreDao dao, ScoreListCallback scoreCallback, int getType) {
            this.dao = dao;
            this.scoreCallback = scoreCallback;
            this.getType = getType;
        }

        @Override
        protected Score doInBackground(String... strings) {
            Score scr = null;

            switch (getType) {


                case BEST_POINTS_LIST:
                    scoreCallback.scoreList(dao.getScoreListPoints(strings[0]));
                    break;

                case BEST_TIMES_LIST:
                    scoreCallback.scoreList(dao.getScoreListTime(strings[0]));
                    break;
            }

            return scr;
        }

        @Override
        protected void onPostExecute(Score score) {
            super.onPostExecute(score);
        }
    }


    private static class GetScore extends AsyncTask <String, Void, Score> {

        private ScoreDao dao;
        private ScoreCallback scoreCallback;
        private int getType;

        GetScore(ScoreDao dao, ScoreCallback scoreCallback, int getType) {
            this.dao = dao;
            this.scoreCallback = scoreCallback;
            this.getType = getType;
        }

        @Override
        protected Score doInBackground(String... strings) {
            Score scr = null;

            switch (getType) {

                case BEST_POINTS:
                    scr = dao.getBestScorePoints(strings[0]);
                    if (scr == null) {
                        scr = new Score(strings[0], (int) 0);
                    }
                    scoreCallback.bestScore(scr);
                    break;

                case BEST_TIME:
                    scr = dao.getBestScoreTime(strings[0]);
                    if (scr == null) {
                        scr = new Score(strings[0], 0l);
                    }
                    scoreCallback.bestScore(scr);
                    break;
            }

            return null;
        }

        //   @Override
        //  protected void onPostExecute(Score score) {
        //   super.onPostExecute(score);
        //  }
    }


//    private static class GetScoreForAll extends AsyncTask <List <String>, Void, List <Score>> {
//
//        private ScoreDao dao;
//        private ScoreForAllCallback scoreCallback;
//        private int getType;
//
//        GetScoreForAll(ScoreDao dao, ScoreForAllCallback scoreCallback, int getType) {
//            this.dao = dao;
//            this.scoreCallback = scoreCallback;
//            this.getType = getType;
//        }
//
//        @Override
//        protected List <Score> doInBackground(List <String>... strings) {
//            Score scr = null;
//            List <Score> l = new ArrayList <>();
//            for (String s : strings[0]) {
//
//
//                switch (getType) {
//
//                    case BEST_POINTS:
//
//                        l.add(dao.getBestScorePoints(s));
//
//
////                    scr = dao.getBestScorePoints(strings[0]);
////                    if (scr == null) {
////                        scr = new Score(strings[0], (int) 0);
////                    }
////                    scoreCallback.bestScore(scr);
//                        break;
////
//                    case BEST_TIME:
//
//                        l.add(dao.getBestScoreTime(s));
//
//
////                    scr = dao.getBestScoreTime(strings[0]);
////                    if (scr == null) {
////                        scr = new Score(strings[0], 0l);
////                    }
////                    scoreCallback.bestScore(scr);
//                        break;
//                }
//            }
//            scoreCallback.scoresForAll(l);
//            return null;
//        }
//
////        @Override
////        protected void onPostExecute(Score score) {
////            super.onPostExecute(score);
////        }
//    }


}
