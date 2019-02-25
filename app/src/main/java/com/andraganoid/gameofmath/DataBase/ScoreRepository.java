package com.andraganoid.gameofmath.DataBase;

import android.content.Context;
import android.os.AsyncTask;


import com.andraganoid.gameofmath.HighScores.Score;

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
        private ScoreListCallback scoreCallback;

        SaveScore(ScoreDao dao, ScoreListCallback scoreCallback) {
            this.dao = dao;
            this.scoreCallback = scoreCallback;
        }

        @Override
        protected Void doInBackground(Score... scores) {

            Score scr = scores[0];
            List <Score> scoreList = new ArrayList <>();

            long lastScoreId = dao.saveScore(scr);

            switch (scr.getScoreType()) {

                case Score.SCORE_TYPE_POINTS:
                    scoreList = dao.getScoreListPoints(scr.getLevelName());
                    if (scoreList.size() > MAX_SCORES_IN_LIST) {
                        dao.deleteOverPoints(scoreList.get(MAX_SCORES_IN_LIST - 1).getScorePoints());
                    }
                    break;

                case Score.SCORE_TYPE_TIME:
                    scoreList = dao.getScoreListTime(scr.getLevelName());
                    if (scoreList.size() > MAX_SCORES_IN_LIST) {
                        dao.deleteOverTimes(scoreList.get(MAX_SCORES_IN_LIST - 1).getScoreMillis());
                    }
                    break;
            }

            scoreCallback.scoreSaved(scoreList, scr.getLevelName(), lastScoreId);
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
            Score scr;
            List <Score> lst;
            switch (getType) {

                case BEST_POINTS:
                    lst = dao.getBestScorePoints(strings[0]);

                    if (lst.size() == 0) {
                        scr = new Score(strings[0], (int) 0);
                    } else {
                        scr = lst.get(0);
                    }
                    scoreCallback.bestScore(scr);
                    break;

                case BEST_TIME:
                    lst = dao.getBestScoreTime(strings[0]);

                    if (lst.size() == 0) {
                        scr = new Score(strings[0], 0L);
                    } else {
                        scr = lst.get(0);
                    }

                    scoreCallback.bestScore(scr);
                    break;
            }

            return null;
        }
    }
}
