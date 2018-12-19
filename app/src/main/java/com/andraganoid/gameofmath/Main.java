package com.andraganoid.gameofmath;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andraganoid.gameofmath.Heavy.HeavyBoard;
import com.andraganoid.gameofmath.Misc.MathBase;
import com.andraganoid.gameofmath.Misc.MathSounds;
import com.andraganoid.gameofmath.Operation.Calc;
import com.google.android.gms.ads.MobileAds;
import com.squareup.leakcanary.LeakCanary;


// TODO

//HEAVY HINT NE RADI
// heavy calc kad ispise ocisti, prvo pise tacno, pa tek onda your score

//BONUSES ARE -1
//??????????????????


// ALERT DIALOG?


// LEADERBOARDS SHOW
// LEADERBOARDS DB&LOGIC

// leaderboards  ON/OFF ispis???

// leaderboards listview OK

// leaderboards on!!//   OK
// leaderboards off!!    OK

//game onpause pogasi sve dijaloge i menije

//privacy policy


//text & code cleaning


public class Main extends AppCompatActivity {
    private ImageView logo_main;
    private ObjectAnimator animator;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();

        new AsyncTask <Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MobileAds.initialize(getApplicationContext(), getString(R.string.AD_MOB_APP_ID));
                MathBase mb = new MathBase(getApplicationContext());
                MathSounds ms = MathSounds.getInstance(getApplicationContext());
                new Calc().initBonuses();
                return null;
            }

        };

        prefsEditor.putBoolean("askForLeaderboardsAtStart", true).apply();

        logo_main = findViewById(R.id.game_logo_main);
        startAnimator();


        //


//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(getApplication());


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

