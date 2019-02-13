package com.andraganoid.gameofmath;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Game.Game;
import com.andraganoid.gameofmath.MathLeaderboards.MathLeaderboards;
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


public class Main extends MathLeaderboards implements View.OnClickListener {
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
        findViewById(R.id.lb_sign_in_button).setOnClickListener(this);
        findViewById(R.id.lb_cancel).setOnClickListener(this);
        findViewById(R.id.lb_again).setOnClickListener(this);

        lb_check_lay = findViewById(R.id.lb_connect_start_dialog);
        logo_main = findViewById(R.id.game_logo_main);

        startAnimator();


//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(getApplication());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lb_sign_in_button:
                prefsEditor.putBoolean("askForLeaderboardsPermission", true).apply();
                // startSignInIntentLeaderboard(REQUEST_CODE_MAIN);
                signInSilentlyLeaderboard(REQUEST_CODE_MAIN);
                break;
            case R.id.lb_cancel:
                prefsEditor.putBoolean("askForLeaderboardsPermission", false).apply();
                goGame();
                break;
            case R.id.lb_again:
                prefsEditor.putBoolean("askForLeaderboardsPermission", true).apply();
                goGame();
                break;


        }


    }

    private class addInit extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.AD_MOB_APP_ID));
            MathBase mb = new MathBase(getApplicationContext());
            MathSounds ms = MathSounds.getInstance(getApplicationContext());
            new Calc().initBonuses();
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


//                if (prefs.getBoolean("leaderboardsPermissionGranted", false)) {
//
//                    signInSilentlyLeaderboard(REQUEST_CODE_MAIN);
//                } else if (prefs.getBoolean("askForLeaderboardsPermission", true)) {
//                    logo_main.setVisibility(View.GONE);
//                    lb_check_lay.setVisibility(View.VISIBLE);
//                } else {
                   goGame();
//                }
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


//    private void checkLeaderboardsPermission() {
//
//
//        if (!prefs.getBoolean("leaderboardsPermission", false)) {
//
//            lb_check_lay.setVisibility(View.VISIBLE);
//
//
////            AlertDialog lad = new AlertDialog.Builder(this).create();
////            lad.setMessage(getString(R.string.lboard_connect));
////            lad.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialogInterface, int i) {
////
////                    startAnimation(findViewById(R.id.lboard_on_off), 1);
////                    prefsEditor
////                            .putBoolean("leaderboardsPermission", true)
////                            .apply();
////                    slp(true);
////
////
////                }
////            });
////            lad.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialogInterface, int i) {
////                    slp(false);
////
////                }
////            });
////            lad.setCancelable(false);
////            lad.show();
////
////
////            ((TextView) lad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
////            //  ((Button) lad.getWindow().findViewById(android.R.id.button1)).setTypeface(alertTypeface);
////            //  ((Button) lad.getWindow().findViewById(android.R.id.button2)).setTypeface(alertTypeface);
//
//
//        }
//// else {
////            startAnimation(findViewById(R.id.lboard_on_off), 1);
////            prefsEditor
////                    .putBoolean("leaderboardsPermission", false)
////                    .apply();
////            slp(true);
////        }
//
//    }


}

