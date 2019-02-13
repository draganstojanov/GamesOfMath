//package com.andraganoid.gameofmath.GoogleServices;
//
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.support.constraint.ConstraintLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//
//import com.andraganoid.gameofmath.Game.GamePlay;
//import com.andraganoid.gameofmath.Operation.Calc;
//import com.andraganoid.gameofmath.R;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.reward.RewardItem;
//import com.google.android.gms.ads.reward.RewardedVideoAd;
//import com.google.android.gms.ads.reward.RewardedVideoAdListener;
//
//import static com.andraganoid.gameofmath.Misc.MathSounds.REWARD;
//
//public class MathAddMob extends GamePlay implements RewardedVideoAdListener {
//
//    public RewardedVideoAd rewardAd;
//    public AdView bottomAd;
//    public String rc;
//    public ConstraintLayout rl;
//    public boolean getReward;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // setContentView(R.layout.activity_math_add_mob);
//
//        rewardAd = MobileAds.getRewardedVideoAdInstance(this);
//        rewardAd.setRewardedVideoAdListener(this);
//
//
//    }
//
//
//    public void loadRewardAd() {
//
//        if (rewardAd != null && !rewardAd.isLoaded()) {
//            rewardAd.loadAd(getString(R.string.AD_MOB_MATH_REWARD), new AdRequest.Builder().build());
//        }
//    }
//
//    public void showRewardAd() {
//
//        if (rewardAd != null && rewardAd.isLoaded()) {
//            if (fireTimer != null) {
//                fireTimer.cancel();
//            }
//            rewardAd.show();
//        }
//    }
//
//    @Override
//    public void onRewardedVideoAdLoaded() {
//        getBonusClick.setBackgroundColor(getResources().getColor(R.color.info));
//        getBonusClick.setTextColor(getResources().getColor(R.color.checked));
//    }
//
//    @Override
//    public void onRewardedVideoAdClosed() {
//        //  loadRewardAd();
//        if (getReward) {
//            getReward = false;
//            play(REWARD);
//            openRewardDialog();
//
//        }
//
//    }
//
//    @Override
//    public void onRewarded(RewardItem rewardItem) {
//
//        //  Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
//        //         rewardItem.getAmount(), Toast.LENGTH_LONG).show();
//
//        //NACRTAJ NAGRADU
//
//
//        getReward = true;
//
//
//        // Reward the user.
//    }
//
//
//    @Override
//    public void onRewardedVideoAdOpened() {
//    }
//
//    @Override
//    public void onRewardedVideoStarted() {
//    }
//
//
//    @Override
//    public void onRewardedVideoAdLeftApplication() {
//    }
//
//    @Override
//    public void onRewardedVideoAdFailedToLoad(int i) {
//    }
//
//    @Override
//    public void onRewardedVideoCompleted() {
//    }
//
//    public void openRewardDialog() {
//        rc = "";
//        (findViewById(R.id.reward_dialog)).setVisibility(View.VISIBLE);
//
//
//    }
//
//    public void chooseReward(View v) {
//
//        switch (v.getId()) {
//            case R.id.reward_e_skip:
//                rc = Calc.EASY_SKIPS;
//                break;
//            case R.id.reward_e_xtra:
//                rc = Calc.EASY_XTRA_TIME;
//                break;
//            case R.id.reward_e_reset:
//                rc = Calc.EASY_RESETS;
//                break;
//            case R.id.reward_h_hint:
//                rc = Calc.HEAVY_HINTS;
//                break;
//            case R.id.reward_h_xtra:
//                rc = Calc.HEAVY_XTRA_TIME;
//                break;
//            case R.id.reward_h_live:
//                rc = Calc.HEAVY_XTRA_LIVES;
//                break;
//            case R.id.reward_submit:
//                (findViewById(R.id.reward_dialog)).setVisibility(View.GONE);
//                new Calc().addRewards(rc);
//                break;
//        }
//
//        for (int i = 1; i < rl.getChildCount() - 1; i++) {
//            rl.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.tv_stroke));
//        }
//        if (v.getId() != R.id.reward_submit) {
//            v.setBackgroundColor(getResources().getColor(R.color.checked));
//        }
//
//
//    }
//
//
//// xxx
////
////    @Override
////    public void onRewardedVideoAdLoaded() {
////
////    }
////
////    @Override
////    public void onRewardedVideoAdOpened() {
////
////    }
////
////    @Override
////    public void onRewardedVideoStarted() {
////
////    }
////
////    @Override
////    public void onRewardedVideoAdClosed() {
////
////    }
////
////    @Override
////    public void onRewarded(RewardItem rewardItem) {
////
////    }
////
////    @Override
////    public void onRewardedVideoAdLeftApplication() {
////
////    }
////
////    @Override
////    public void onRewardedVideoAdFailedToLoad(int i) {
////
////    }
////
////    @Override
////    public void onRewardedVideoCompleted() {
////
////    }
//
//
//    public void loadFullscreenAd() {
//        if (!fullscreenAd.isLoaded()) {
//            fullscreenAd.loadAd(new AdRequest.Builder().build());
//        }
//    }
//
//    public void showFullscreenAd() {
//        if (fullscreenAd != null && fullscreenAd.isLoaded()) {
////            if (fireTimer != null) {
////                fireTimer.cancel();
////            }
//            fullscreenIsShowed = true;
//            adIsShowing = true;
//            fullscreenAd.show();
//        } else {
//            goMain = false;
//            finish();
//        }
//
//    }
//
//}
