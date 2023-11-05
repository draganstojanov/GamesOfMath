package com.andraganoid.gameofmath.Game;

import static com.andraganoid.gameofmath.Misc.Sounds.REWARD;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.andraganoid.gameofmath.DataBase.Bonus;
import com.andraganoid.gameofmath.DataBase.BonusRepository;
import com.andraganoid.gameofmath.Easy.EasySettings;
import com.andraganoid.gameofmath.Fast.FastSettings;
import com.andraganoid.gameofmath.Heavy.HeavySettings;
import com.andraganoid.gameofmath.Misc.About;
import com.andraganoid.gameofmath.Practice.PracticeSettings;
import com.andraganoid.gameofmath.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;

public class Game extends GamePlay {

    Intent intent;
    private RewardedAd rewardedAd;
    private AdView bottomAd;
    private TextView getBonusClick;
    private boolean goSettings;
    private String rc;
    private ConstraintLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getBonusClick = findViewById(R.id.get_bonus_btn);
//        bottomAd = findViewById(R.id.add_view_bottom_game);
//        bottomAd.loadAd(new AdRequest.Builder().build());
        rl = findViewById(R.id.reward_dialog);
     adsInit();

//        AdRequest adRequest = new AdRequest.Builder().build();
//        RewardedAd.load(this, getString(R.string.ad_mob_math_reward), adRequest, new RewardedAdLoadCallback() {
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                Log.d("RewardedAdError", loadAdError.toString());
//                rewardedAd = null;
//            }
//
//            @Override
//            public void onAdLoaded(@NonNull RewardedAd ad) {
//                super.onAdLoaded(rewardedAd);
//                rewardedAd = ad;
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        turnTheScreenOff();
        soundState();
        getBonusClick.setVisibility(View.VISIBLE);

//        adsInit();

//      bottomAd.loadAd(new AdRequest.Builder().build());
//        AdRequest adRequest = new AdRequest.Builder().build();
//        RewardedAd.load(this, getString(R.string.ad_mob_math_reward), adRequest, new RewardedAdLoadCallback() {
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                Log.d("RewardedAdError", loadAdError.toString());
//                rewardedAd = null;
//            }
//
//            @Override
//            public void onAdLoaded(@NonNull RewardedAd ad) {
//                super.onAdLoaded(rewardedAd);
//                rewardedAd = ad;
//            }
//        });
    }


    protected void onPause() {
        bottomAd.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        bottomAd.destroy();
        super.onDestroy();
    }

    private void adsInit() {
//        new Thread(() -> {    }).start();



        var testDevices = new ArrayList<String>();
        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);

        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();

        MobileAds.setRequestConfiguration(requestConfiguration);
        MobileAds.initialize(getApplicationContext(), initializationStatus -> {

            for (var entry : initializationStatus.getAdapterStatusMap().entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                Log.d("ADDMM-1",entry.getKey() + "/" + entry.getValue());
                Log.d("ADDMM-2", entry.getValue().getDescription());
                Log.d("ADDMM-3",entry.getValue().getInitializationState().name());
                Log.d("ADDMM-4", String.valueOf(entry.getValue().getLatency()));
            }


        });

        bottomAd = findViewById(R.id.add_view_bottom_game);
        bottomAd.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, getString(R.string.ad_mob_math_reward), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("RewardedAdError", loadAdError.toString());
                rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                super.onAdLoaded(rewardedAd);
                rewardedAd = ad;
            }
        });


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

    @Override
    public void onBackPressed() {
        if (goHiScore) {
            goHiScore = false;
            findViewById(R.id.highscore_table).setVisibility(View.GONE);
        } else if (goSettings) {
            goSettings = false;
            findViewById(R.id.lboards_exp_list_wrapper).setVisibility(View.GONE);
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
        goSettings = true;
        highScoresList();
    }

    public void goCloseHsTable(View v) {
        findViewById(R.id.highscore_table).setVisibility(View.GONE);
    }

    public void goLogoEffect(View v) {
        startAnimation(findViewById(R.id.logo), 1);
    }

    public void goGetBonus(View v) {
        if (rewardedAd != null) {

            long lastReward = prefs.getLong("rewardTime", 0L);
            long nextReward = System.currentTimeMillis() - lastReward;

            if (nextReward < 60000) {
                String msg = String.format("%s %s %s", getString(R.string.next_reward_in), 60 - (int) nextReward / 1000, getString(R.string.seconds_short));
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            } else {
                if (prefs.getBoolean("checkIfWatchBonusAd", true)) {
                    openAdConfirmDialog();
                } else {
                    showRewardAd();
                }
            }

        } else {
            String msg = getString(R.string.ad_not_ready);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }


    private void showRewardAd() {
        if (fireTimer != null) {
            fireTimer.cancel();
        }
        rewardedAd.show(this, rewardItem -> {
            play(REWARD);
            openRewardDialog();
            prefsEditor.putLong("rewardTime", System.currentTimeMillis()).apply();
        });
    }


    private void openAdConfirmDialog() {
        View cboxView = getLayoutInflater().inflate(R.layout.bonus_ad_checkbox, null);
        CheckBox cbox = cboxView.findViewById(R.id.bonus_ad_checkbox);
        cbox.setTypeface(alertTypeface);
        AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        adb.setTitle(getResources().getString(R.string.get_bonus))
                .setMessage(getString(R.string.watch_ad))
                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> showRewardAd())
                .setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
                })
                .setView(cboxView)
                .setCancelable(true)
                .show();
        cbox.setOnCheckedChangeListener((buttonView, isChecked) -> prefsEditor.putBoolean("checkIfWatchBonusAd", !isChecked).apply());
    }


    public void openRewardDialog() {
        rc = "";
        (findViewById(R.id.reward_dialog)).setVisibility(View.VISIBLE);
    }

    public void chooseReward(View v) {
        switch (v.getId()) {
            case R.id.reward_e_skip:
                rc = Bonus.EASY_SKIPS;
                break;
            case R.id.reward_e_xtra:
                rc = Bonus.EASY_XTRA_TIME;
                break;
            case R.id.reward_e_reset:
                rc = Bonus.EASY_RESETS;
                break;
            case R.id.reward_h_hint:
                rc = Bonus.HEAVY_HINTS;
                break;
            case R.id.reward_h_xtra:
                rc = Bonus.HEAVY_XTRA_TIME;
                break;
            case R.id.reward_h_live:
                rc = Bonus.HEAVY_XTRA_LIVES;
                break;
            case R.id.reward_submit:
                (findViewById(R.id.reward_dialog)).setVisibility(View.GONE);
                new BonusRepository(getApplicationContext()).setRewardBonus(rc, Bonus.INCREASE);
                break;
        }
        for (int i = 1; i < rl.getChildCount() - 1; i++) {
            rl.getChildAt(i).setBackground(ContextCompat.getDrawable(this, R.drawable.tv_stroke));
        }
        if (v.getId() != R.id.reward_submit) {
            v.setBackgroundColor(getResources().getColor(R.color.checked));
        }
    }

}
