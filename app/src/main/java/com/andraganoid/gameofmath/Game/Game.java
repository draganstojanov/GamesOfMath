package com.andraganoid.gameofmath.Game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static com.andraganoid.gameofmath.Misc.Sounds.REWARD;

public class Game extends GamePlay implements RewardedVideoAdListener {

    Intent intent;
    private RewardedVideoAd rewardAd;
    private AdView bottomAd;
    private TextView getBonusClick;
    private boolean goSettings;
    private boolean getReward;
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
        soundState();
        rewardAd.resume(this);
        if (rewardAd.isLoaded()) {
            getBonusClick.setVisibility(View.VISIBLE);
        } else {
            loadRewardAd();
        }
        bottomAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getBonusClick = findViewById(R.id.get_bonus_btn);
        bottomAd = findViewById(R.id.add_view_bottom_game);
        rl = findViewById(R.id.reward_dialog);
        rewardAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardAd.setRewardedVideoAdListener(this);
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

    public void goGetBonus(View v) {
        if (rewardAd != null && rewardAd.isLoaded()) {
            if (prefs.getBoolean("checkIfWatchBonusAd", true)) {
                View cboxView = getLayoutInflater().inflate(R.layout.bonus_ad_checkbox, null);
                CheckBox cbox = cboxView.findViewById(R.id.bonus_ad_checkbox);
                cbox.setTypeface(alertTypeface);
                AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                adb.setTitle(getResources().getString(R.string.get_bonus));
                adb.setMessage(getString(R.string.watch_ad));
                adb.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showRewardAd();
                    }
                });
                adb.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                adb.setView(cboxView);
                adb.setCancelable(true);
                adb.show();
                cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        prefsEditor.putBoolean("checkIfWatchBonusAd", !isChecked).apply();
                    }
                });
            } else {
                showRewardAd();
            }
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
        getBonusClick.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        getBonusClick.setVisibility(View.GONE);
        if (getReward) {
            getReward = false;
            play(REWARD);
            openRewardDialog();
        }
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
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
            rl.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.tv_stroke));
        }
        if (v.getId() != R.id.reward_submit) {
            v.setBackgroundColor(getResources().getColor(R.color.checked));
        }
    }
}
