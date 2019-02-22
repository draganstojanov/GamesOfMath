package com.andraganoid.gameofmath;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.andraganoid.gameofmath.DataBase.BonusRepository;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Misc.Sounds;
import com.google.android.gms.ads.MobileAds;


// TODO

// game over - ispis hs tabele nakratko pa reklama - resi u reklami!!!

//game onpause pogasi sve dijaloge i menije

//privacy policy


//text & code cleaning


public class Main extends AppCompatActivity {
    private ImageView logo_main;
    private ObjectAnimator animator;

    private ConstraintLayout lb_check_lay;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();

        new addInit().execute();

        //          new ScoreRepository(getApplicationContext()).getBestPoints("fast_calc_01",sc);

       // int score = 666666;

       // for (int i = 0; i < 51; i++) {
         //   new ScoreRepository(getApplicationContext()).saveScore(new Score("fast_calc_02", score), sc);
      //  }


        //  new ScoreRepository(getApplicationContext()).getBestPoints("fast_calc_02",sc);


     //   lb_check_lay = findViewById(R.id.lb_connect_start_dialog);
        logo_main = findViewById(R.id.game_logo_main);


//        new BonusRepository(getApplicationContext()).getBonus();
//
//        Bonus test = new Bonus();
//        test.setEasy_skips(99);
//        new BonusRepository(getApplicationContext()).saveBonus(test);
//        new BonusRepository(getApplicationContext()).getBonus();

        startAnimator();


//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(getApplication());


    }


    private class addInit extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            new BonusRepository(getApplicationContext()).initBonuses();
            MobileAds.initialize(getApplicationContext(), getString(R.string.AD_MOB_APP_ID));
            //  MathBase mb = new MathBase(getApplicationContext());
            Sounds ms = Sounds.getInstance(getApplicationContext());
            //  new Calc().initBonuses();

            return null;
        }
    }

    private void goGame() {

        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startAnimator() {
        animator = ObjectAnimator.ofFloat(logo_main, View.ALPHA, 0f, 1f);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                goGame();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.setDuration(5000).start();
    }


//    ScoreCallback sc = new ScoreCallback() {
//        @Override
//        public void scoreSaved(List <Score> scoreList, String levelName) {
//
//            System.out.println("SCORE SAVED:" + scoreList.size() + "-" + levelName);
//
//            for (int i = 0; i < scoreList.size(); i++) {
//                System.out.println("SCORE LIST: " + i + "=" + scoreList.get(i).getScorePoints());
//            }
//
//
//            new ScoreRepository(getApplicationContext()).getBestPoints("fast_calc_02", sc);
//        }
//
//        @Override
//        public void bestPoints(Score bestScorePoints) {
//
//            System.out.println("SCORE POINTS:" + bestScorePoints.getScorePoints());
//
//
//        }
//
//        @Override
//        public void bestPointsList(List <Score> scoreListPoints) {
//
//        }
//
//        @Override
//        public void bestTimes(Score bestScoreTime) {
//
//        }
//
//        @Override
//        public void bestTimesList(List <Score> scoreListTime) {
//
//        }
//    };


}

