package com.andraganoid.gameofmath;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.Misc.MathSounds;
import com.andraganoid.gameofmath.Operation.Calc;
import com.google.android.gms.ads.MobileAds;
import com.squareup.leakcanary.LeakCanary;


// TODO

//HEAVY HINT NE RADI

//checkLeaderboardDesire
//practice i heavy again or leaderboard

// heavy calc kad ispise ocisti, prvo pise tacno, pa tek onda your score


// LEADERBOARDS SHOW
// LEADERBOARDS DB&LOGIC
// leaderboards listview
// leaderboards on/off OK OK!!!

//game onpause pogasi sve dijaloge i menije




//text & code cleaning


public class Main extends AppCompatActivity {
    ImageView logo_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MobileAds.initialize(getApplicationContext(), getString(R.string.AD_MOB_APP_ID));

                return null;
            }

        };


        MathBase mb = new MathBase(getApplicationContext());
        MathSounds ms = MathSounds.getInstance(getApplicationContext());


        new Calc().initBonuses();

        logo_main = findViewById(R.id.game_logo_main);


        ObjectAnimator animator =
                ObjectAnimator.ofFloat(logo_main, View.ALPHA, 0f, 1f);

        animator.setDuration(5000)
                .start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //checkLeaderboardDesire();
                goGame();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });


//        logo_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goGame();
//            }
//        });


//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(getApplication());


    }


    private void checkLeaderboardDesire(){

        // alert da li zelis da s nakacis


    }


    private void goGame() {

        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}

