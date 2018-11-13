package com.andraganoid.gameofmath;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.ads.MobileAds;
import com.squareup.leakcanary.LeakCanary;


//ca-app-pub-7832187166369477~6543894378

// TODO
// showscore odbrojavanje rol cifara V2
//background V2 ???

//hiscores  googleplay
//reklame proveri

//help text

//text
//intro


//CHOOSE 1 REWARD

//SOUNDS:  beep za start,check first not play???


public class Main extends AppCompatActivity {
    ImageView logo_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MathBase mb =  new MathBase(getApplicationContext());
        MathSounds ms = MathSounds.getInstance(getApplicationContext());




        new Calc().initBonuses();

        logo_main = findViewById(R.id.game_logo_main);


        //    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        ObjectAnimator animator = ObjectAnimator
                .ofFloat(logo_main, View.ALPHA, 0f, 1f);

        animator
                .setDuration(5000)
                .start();

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


//        logo_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goGame();
//            }
//        });


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());


    }

    private void goGame() {

        Log.d("TRACE","1");

        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}

