package com.andraganoid.gameofmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.squareup.leakcanary.LeakCanary;


// TODO
// showscore odbrojavanje rol cifara V2

// sounds : lost life,get bonus life,gameover
//firebase chrash
// hiscores  googleplay
//ispis bonusa sredi
//help
//background
//text
//policy
//intro



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






        setContentView(R.layout.main);


//
//       GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
//                .addApi(Games.API)
//                .addScope(Games.SCOPE_GAMES)
//                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                        Toast.makeText(Main.this, "Could not connect to Play games services", Toast.LENGTH_LONG).show();
//
//
//                        finish();
//                    }
//                }).build();


        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


}

