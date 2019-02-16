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

import com.andraganoid.gameofmath.DataBase.Bonus;
import com.andraganoid.gameofmath.DataBase.Repo;
import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.Misc.MathSounds;
import com.andraganoid.gameofmath.Operation.Calc;
import com.google.android.gms.ads.MobileAds;


// TODO

//HEAVY HINT NE RADI
// heavy calc kad ispise ocisti, prvo pise tacno, pa tek onda your score

//BONUSES ARE -1
//??????????????????


// ALERT DIALOG?


// LEADERBOARDS SHOW
// LEADERBOARDS DB&LOGIC

// leaderboards  ON/OFF ispis???OK

// leaderboards listview OK

// leaderboards on!!//   OK
// leaderboards off!!    OK

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

        lb_check_lay = findViewById(R.id.lb_connect_start_dialog);
        logo_main = findViewById(R.id.game_logo_main);



//        new Repo(getApplicationContext()).getBonus();
//
//        Bonus test = new Bonus();
//        test.setEasy_skips(99);
//        new Repo(getApplicationContext()).saveBonus(test);
//        new Repo(getApplicationContext()).getBonus();

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
           new Repo(getApplicationContext()).initBonuses();
            MobileAds.initialize(getApplicationContext(), getString(R.string.AD_MOB_APP_ID));
          //  MathBase mb = new MathBase(getApplicationContext());
            MathSounds ms = MathSounds.getInstance(getApplicationContext());
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


}

