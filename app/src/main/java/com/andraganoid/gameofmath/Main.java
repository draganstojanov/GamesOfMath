package com.andraganoid.gameofmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.squareup.leakcanary.LeakCanary;




// TODO
// showscore odbrojavanje rol cifara V2

//firebase crash
//hiscores  googleplay
//reklame

//ispis bonusa sredi
//help text
//background
//text
//intro
//scores

//SOUNDS: lostlife, gameover, get bonuses, beep za start

// game_board.xml OK

public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());





        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


}

