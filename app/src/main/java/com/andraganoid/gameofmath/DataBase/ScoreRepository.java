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

    public void saveScore(Score score, ScoreCallback scoreCallback) {
        new SaveScore(scoreDao, scoreCallback).execute(score);
    }

    public void getBestPoints(String levelName, ScoreCallback scoreCallback) {
        new GetScore(scoreDao, scoreCallback, BEST_POINTS).execute(levelName);
    }

    public void getBestPointsList(String levelName, ScoreListCallback scoreCallback) {
        new GetScoreList(scoreDao, scoreCallback, BEST_POINTS_LIST).execute(levelName);
    }

    public void getBestTime(String levelName, ScoreCallback scoreCallback) {
        new GetScore(scoreDao, scoreCallback, BEST_TIME).execute(levelName);
    }

    public void getBestTimesList(String levelName, ScoreListCallback scoreCallback) {
        new GetScoreList(scoreDao, scoreCallback, BEST_TIMES_LIST).execute(levelName);
    }


    private static class SaveScore extends AsyncTask <Score, Void, Void> {

        private ScoreDao dao;
        private ScoreCallback scoreCallback;

        SaveScore(ScoreDao dao, ScoreCallback scoreCallback) {
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
                    break;

                case Score.SCORE_TYPE_TIME:
                    scoreList = dao.getScoreListTime(scr.getLevelName());
                    break;
            }
            if (scoreList.size() > MAX_SCORES_IN_LIST) {

                dao.deleteScore(scoreList.get(MAX_SCORES_IN_LIST));
                scoreList.remove(MAX_SCORES_IN_LIST);
            }

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

            return scr;
        }

        @Override
        protected void onPostExecute(Score score) {
            super.onPostExecute(score);
        }
    }

}
