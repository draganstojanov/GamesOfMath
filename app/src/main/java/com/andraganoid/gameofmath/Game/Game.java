package com.andraganoid.gameofmath.Game;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andraganoid.gameofmath.Easy.EasySettings;
import com.andraganoid.gameofmath.Fast.FastSettings;
import com.andraganoid.gameofmath.Heavy.HeavySettings;
import com.andraganoid.gameofmath.Misc.About;
import com.andraganoid.gameofmath.Operation.Calc;
import com.andraganoid.gameofmath.Practice.PracticeSettings;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static com.andraganoid.gameofmath.Misc.MathSounds.REWARD;

public class Game extends GamePlay implements RewardedVideoAdListener {

    Intent intent;
    private RewardedVideoAd rewardAd;
    private AdView bottomAd;
    private TextView getBonusClick;
    private boolean goSettings;
    private boolean getReward;

    // public SharedPreferences prefs;
    //  public SharedPreferences.Editor prefsEditor;

    private String rc;
    private ConstraintLayout rl;


    @Override
    protected void onPause() {
        super.onPause();
        rewardAd.pause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rewardAd.destroy(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnTheScreenOff();

        rewardAd.resume(this);
        if (rewardAd.isLoaded()) {
            getBonusClick.setBackgroundColor(getResources().getColor(R.color.info));
            getBonusClick.setTextColor(getResources().getColor(R.color.checked));
        } else {
            loadRewardAd();
        }
        bottomAd.loadAd(new AdRequest.Builder().build());

//        if (prefs.getBoolean("leaderboardsPermissionGranted", false)) {
//            if (!isSignedInLeaderboard()) {
//                signInSilentlyLeaderboard(REQUEST_CODE_GAME);
//            }
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        //  prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //  prefsEditor = prefs.edit();
        getBonusClick = findViewById(R.id.get_bonus_btn);
        bottomAd = findViewById(R.id.add_view_bottom_game);
        rl = findViewById(R.id.reward_dialog);
        rewardAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardAd.setRewardedVideoAdListener(this);
        // checkForLeaderboards();

    }


    public void goPractice(View v) {
        intent = new Intent(this, PracticeSettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goArcade(View v) {
        intent = new Intent(this, FastSettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goEasy(View v) {
        intent = new Intent(this, EasySettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goHeavy(View v) {
        intent = new Intent(this, HeavySettings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToggleLeaderboards(View v) {

        if (isSignedInLeaderboard()) {
            signOutLeaderboard();
        } else {
            //startSignInIntentLeaderboard(REQUEST_CODE_SETTINGS);
            signInSilentlyLeaderboard(REQUEST_CODE_SETTINGS);
        }
    }


//    public void goSetLeaderboardPermission(View v) {
//
//        if (!prefs.getBoolean("leaderboardsPermission", false)) {
//
//            AlertDialog lad = new AlertDialog.Builder(Game.this).create();
//            lad.setMessage(getString(R.string.lboard_connect));
//            lad.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    startAnimation(findViewById(R.id.lboard_on_off), 1);
//                    prefsEditor
//                            .putBoolean("leaderboardsPermission", true)
//                            .apply();
//                    slp(true);
//
//
//                }
//            });
//            lad.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    slp(false);
//
//                }
//            });
//            lad.setCancelable(false);
//            lad.show();
//
//
//            ((TextView) lad.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
//          //  ((Button) lad.getWindow().findViewById(android.R.id.button1)).setTypeface(alertTypeface);
//          //  ((Button) lad.getWindow().findViewById(android.R.id.button2)).setTypeface(alertTypeface);
//
//
//        } else {
//            startAnimation(findViewById(R.id.lboard_on_off), 1);
//            prefsEditor
//                    .putBoolean("leaderboardsPermission", false)
//                    .apply();
//            slp(true);
//        }
//
//    }


    @Override
    public void onBackPressed() {
        if (goSettings) {
            goSettings = false;
        } else {
            super.onBackPressed();
        }
    }

    public void goAbout(View v) {

        intent = new Intent(this, About.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goSettings(View v) {

        findViewById(R.id.game_settings).setVisibility(View.VISIBLE);
        goSettings = true;
        soundState();
        if (isSignedInLeaderboard()) {
            ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.on));
        } else {
            ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.off));
        }


        //  slp(false);
    }

    @Override
    public void slp(boolean onoff) {

        int o = onoff ? R.string.on : R.string.off;
        ((TextView) findViewById(R.id.lboard_on_off)).setText(o);
        startAnimation(findViewById(R.id.lboard_on_off), 1);
    }

//    public void slp(boolean check) {
//
//        if (prefs.getBoolean("leaderboardsPermission", false)) {
//            ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.on));
//            if (check) {
//                signInSilentlyLeaderboard();
//                xxx
//            }
//
//        } else {
//            ((TextView) findViewById(R.id.lboard_on_off)).setText(getString(R.string.off));
//            if (check) {
//                signOutLeaderboard();
//            }
//        }
//
//    }

    public void goSettingsOk(View v) {
        findViewById(R.id.game_settings).setVisibility(View.GONE);
    }


    public void goGetBonus(View v) {

        if (rewardAd != null && rewardAd.isLoaded()) {
            showRewardAd();
        }
    }

    public void goLogoEffect(View v) {
        startAnimation(findViewById(R.id.logo), 1);

    }


    private void loadRewardAd() {

        if (rewardAd != null && !rewardAd.isLoaded()) {
            rewardAd.loadAd(getString(R.string.AD_MOB_MATH_REWARD), new AdRequest.Builder().build());
        }
    }

    private void showRewardAd() {

        if (rewardAd != null && rewardAd.isLoaded()) {
            if (fireTimer != null) {
                fireTimer.cancel();
            }
            rewardAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        getBonusClick.setBackgroundColor(getResources().getColor(R.color.info));
        getBonusClick.setTextColor(getResources().getColor(R.color.checked));
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //  loadRewardAd();
        if (getReward) {
            getReward = false;
            play(REWARD);
            openRewardDialog();

        }


    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        //  Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
        //         rewardItem.getAmount(), Toast.LENGTH_LONG).show();

        //NACRTAJ NAGRADU


        getReward = true;


        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
    }

    @Override
    public void onRewardedVideoCompleted() {
    }

    public void openRewardDialog() {
        rc = "";
        (findViewById(R.id.reward_dialog)).setVisibility(View.VISIBLE);
    }

    public void chooseReward(View v) {

        switch (v.getId()) {
            case R.id.reward_e_skip:
                rc = Calc.EASY_SKIPS;
                break;
            case R.id.reward_e_xtra:
                rc = Calc.EASY_XTRA_TIME;
                break;
            case R.id.reward_e_reset:
                rc = Calc.EASY_RESETS;
                break;
            case R.id.reward_h_hint:
                rc = Calc.HEAVY_HINTS;
                break;
            case R.id.reward_h_xtra:
                rc = Calc.HEAVY_XTRA_TIME;
                break;
            case R.id.reward_h_live:
                rc = Calc.HEAVY_XTRA_LIVES;
                break;
            case R.id.reward_submit:
                (findViewById(R.id.reward_dialog)).setVisibility(View.GONE);
                new Calc().addRewards(rc);
                break;
        }

        for (int i = 1; i < rl.getChildCount() - 1; i++) {
            rl.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.tv_stroke));
        }
        if (v.getId() != R.id.reward_submit) {
            v.setBackgroundColor(getResources().getColor(R.color.checked));
        }


    }


//    void checkForLeaderboards() {
//
//
//        if (prefs.getBoolean("askForLeaderboards", true) && prefs.getBoolean("askForLeaderboardsAtStart", false)) {
//
//            prefsEditor.putBoolean("askForLeaderboardsAtStart", false).apply();
//
//
//            android.app.AlertDialog adb = new android.app.AlertDialog.Builder(this).create();
//            // adb.setTitle(getString(R.string.out_of_lives));
//            adb.setMessage(getString(R.string.join_leaderboards));
//            adb.setButton(Dialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    prefsEditor.putBoolean("leaderboardsPermission", true).apply();
//                    startSignInIntentLeaderboard();
//
//                    //positive
//                    //  goMenu(false);
//
//                }
//            });
//
//            adb.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    //negative
//
//                    goMenu(false);
//                }
//            });
//
//            adb.setButton(Dialog.BUTTON_NEUTRAL, getString(R.string.ask_later), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    //ask later
//
//                    goMenu(true);
//
//                }
//            });
//
//            adb.setCancelable(false);
//            adb.show();
//
//
////            int titleId = getResources().getIdentifier("alertTitle", "id", "android");
////            if (titleId > 0) {
////                TextView title = (TextView) adb.findViewById(titleId);
////                if (title != null) {
////                    title.setTypeface(alertTypeface);
////                }
////            }
//
//            ((TextView) adb.getWindow().findViewById(android.R.id.message)).setTypeface(alertTypeface);
////            ((Button) adb.getWindow().findViewById(android.R.id.button1)).setTypeface(alertTypeface);
////            ((Button) adb.getWindow().findViewById(android.R.id.button2)).setTypeface(alertTypeface);
////            ((Button) adb.getWindow().findViewById(android.R.id.button3)).setTypeface(alertTypeface);
//
//    //adb.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.checked)));
//
//        } else {
//            goMenu(false);
//        }
//    }


//    public void goMenu(boolean ask) {
//        prefsEditor.putBoolean("askForLeaderboards", ask).apply();
//        (findViewById(R.id.first_logo)).setVisibility(View.GONE);
//    }

}
