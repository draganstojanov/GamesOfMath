package com.andraganoid.gameofmath;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


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

//SOUNDS:  beep za start,check first not play


public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());



        MathBase mb = new MathBase(getApplicationContext());
        new Calc().initBonuses();

        Intent intent = new Intent(this, Game.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


}

