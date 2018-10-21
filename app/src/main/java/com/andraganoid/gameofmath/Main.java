package com.andraganoid.gameofmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


// TODO
// showscore odbrojavanje
// tiner out zadnjih 10 sekudi zvuk
// sounds : use bonus,get bonus,good answer,wrong answer,lost life,firework
// hiscores  googleplay
//ispis bonusa sredi
//help



public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


    }

